package com.shtand.aggregator.task.backend.reader;

import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CrmAggregatorLogic {

    private final AggregatedCaseFactory aggregatedCaseFactory;

    public CrmAggregatorLogic(CrmCaseFetchService crmCaseFetchService, AggregatedCaseFactory aggregatedCaseFactory) {
        this.aggregatedCaseFactory = aggregatedCaseFactory;
    }

    public List<AggregatedCase> aggregateCases(List<BaseCase> baseCases) {
        Map<String, AggregatedCase> caseMap = baseCases.stream().collect(Collectors.toMap(this::keyMapper, aggregatedCaseFactory::createAggregatedCase, aggregatedCaseFactory::addCase));
        return new ArrayList<>(caseMap.values());
    }


    private String keyMapper(BaseCase baseCase) {
        //This is the aggregation logic
        return baseCase.getCreatedErrorCode() + "_" + baseCase.getProviderId();
    }
}
