package com.shtand.aggregator.task.backend.service;

import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.AggregatedCaseDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AggregatedCaseConverter implements Function<AggregatedCase,AggregatedCaseDto> {

    @Override
    public AggregatedCaseDto apply(AggregatedCase aggregatedCase) {
        return AggregatedCaseDto.builder()
                .caseIds(aggregatedCase.getCaseIds())
                .errorCode(aggregatedCase.getErrorCode())
                .id(aggregatedCase.getId())
                .numberOfSupportCases(aggregatedCase.getNumberOfSupportCases())
                .productsAffected(aggregatedCase.getProductsAffected())
                .providerName(aggregatedCase.getProviderName())
                .caseStatus(aggregatedCase.getCaseStatus())
                .build();
    }
}
