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

    public CrmAggregatorLogic(AggregatedCaseFactory aggregatedCaseFactory) {
        this.aggregatedCaseFactory = aggregatedCaseFactory;
    }

    public List<AggregatedCase> aggregateCases(List<BaseCase> baseCases) {
        Map<String, AggregatedCase> caseMap = baseCases.stream()
                .map(aggregatedCaseFactory::createAggregatedCase)
                .collect(Collectors.toMap(a -> a.getId(), a -> a, aggregatedCaseFactory::addCase));
        return new ArrayList<>(caseMap.values());
    }

}
