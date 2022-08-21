package com.shtand.aggregator.task.backend.reader;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.apache.commons.compress.utils.Sets;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class AggregatedCaseFactory {

    public AggregatedCase createAggregatedCase(BaseCase baseCase) {
        String errorCode = String.valueOf(baseCase.getCaseId());
        Set<String> productsAffected = Sets.newHashSet(baseCase.getProductName());
        ArrayList<Long> caseIds = Lists.newArrayList(baseCase.getCaseId());
        return AggregatedCase.builder()
                .numberOfSupportCases(1)
                .errorCode(errorCode)
                .productsAffected(productsAffected)
                .caseIds(caseIds)
                .build();
    }

    public AggregatedCase addCase(AggregatedCase aggregatedCase1, AggregatedCase aggregatedCase2) {
        //Smelly optimization. I know that it's an arraylist because I've created it as such in the factory method above
        aggregatedCase1.getCaseIds().addAll(aggregatedCase2.getCaseIds());
        aggregatedCase1.getProductsAffected().addAll(aggregatedCase2.getProductsAffected());
        return aggregatedCase1;
    }

}
