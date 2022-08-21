package com.shtand.aggregator.task.backend;

import com.shtand.aggregator.task.backend.model.*;
import com.shtand.aggregator.task.backend.reader.CrmAggregatorLogic;
import com.shtand.aggregator.task.backend.reader.CrmCaseFetchService;
import com.shtand.aggregator.task.backend.repo.AggregatedCaseRepository;
import com.shtand.aggregator.task.backend.service.AggregatedCaseConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CrmService {

    private long lastRefreshTimestamp;

    private static final long minimalDelay = 60000 * 15;
    private CrmCaseFetchService crmCaseFetchService;

    private CrmAggregatorLogic crmAggregatorLogic;

    private AggregatedCaseRepository aggregatedCaseRepository;

    private AggregatedCaseConverter aggregatedCaseConverter;

    public CrmService(CrmCaseFetchService crmCaseFetchService, CrmAggregatorLogic crmAggregatorLogic, AggregatedCaseRepository aggregatedCaseRepository, AggregatedCaseConverter aggregatedCaseConverter) {
        this.crmCaseFetchService = crmCaseFetchService;
        this.crmAggregatorLogic = crmAggregatorLogic;
        this.aggregatedCaseRepository = aggregatedCaseRepository;
        this.aggregatedCaseConverter = aggregatedCaseConverter;
    }

    @Scheduled(initialDelay = 10000, fixedDelayString = "${refresh.delay}")
    public synchronized RefreshResponse refreshIfNeeded() {
        if (System.currentTimeMillis() - lastRefreshTimestamp < minimalDelay) {
            log.info("Last refresh was less then 15 minutes ago. Not refreshing");
            return RefreshResponse.builder()
                    .lastRefreshTimestamp(lastRefreshTimestamp)
                    .skippedRefresh(true)
                    .build();
        }
        log.info("Refresh crm database");
        return refreshCases();
    }

    private RefreshResponse refreshCases() {
        try {
            List<BaseCase> baseCases = crmCaseFetchService.fetchCases();
            List<AggregatedCase> aggregatedCases = crmAggregatorLogic.aggregateCases(baseCases);
            //Ideally - we want to do it seamlessly, but it takes too long to implement something like that (using versions for example)
            aggregatedCaseRepository.deleteAll();
            aggregatedCaseRepository.saveAll(aggregatedCases);
            lastRefreshTimestamp = System.currentTimeMillis();
            return RefreshResponse.builder()
                    .lastRefreshTimestamp(lastRefreshTimestamp)
                    .totalAggregatedCaseCount(aggregatedCases.size()).build();
        } catch (Exception e) {
            log.error("Failed to refresh CRM cases",e);
            throw new CrmException(e,"Failed to refresh CRM cases");
        }
    }

    public List<AggregatedCaseDto> getAggregations() {
        return aggregatedCaseRepository.findAll().stream()
                .map(aggregatedCaseConverter)
                .collect(Collectors.toList());
    }
}
