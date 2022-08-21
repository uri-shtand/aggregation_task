package com.shtand.aggregator.task.backend;

import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.BaseCase;
import com.shtand.aggregator.task.backend.model.RefreshResponse;
import com.shtand.aggregator.task.backend.reader.CrmAggregatorLogic;
import com.shtand.aggregator.task.backend.reader.CrmCaseFetchService;
import com.shtand.aggregator.task.backend.repo.AggregatedCaseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrmService {

    private CrmCaseFetchService crmCaseFetchService;

    private CrmAggregatorLogic crmAggregatorLogic;

    private AggregatedCaseRepository aggregatedCaseRepository;

    public CrmService(CrmCaseFetchService crmCaseFetchService, CrmAggregatorLogic crmAggregatorLogic, AggregatedCaseRepository aggregatedCaseRepository) {
        this.crmCaseFetchService = crmCaseFetchService;
        this.crmAggregatorLogic = crmAggregatorLogic;
        this.aggregatedCaseRepository = aggregatedCaseRepository;
    }

    public RefreshResponse refresh() {
        List<BaseCase> baseCases = crmCaseFetchService.fetchCases();
        List<AggregatedCase> aggregatedCases = crmAggregatorLogic.aggregateCases(baseCases);
        //Ideally - we want to do it seamlessly, but it takes too long to implement something like that (using versions for example)
        aggregatedCaseRepository.deleteAll();
        aggregatedCaseRepository.saveAll(aggregatedCases);
        return RefreshResponse.builder().totalAggregatedCaseCount(aggregatedCases.size()).build();
    }
}
