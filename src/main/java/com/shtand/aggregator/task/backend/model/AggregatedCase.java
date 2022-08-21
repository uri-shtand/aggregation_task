package com.shtand.aggregator.task.backend.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class AggregatedCase {

    private String errorCode;
    private String providerName;
    private Set<String> productsAffected;
    private int numberOfSupportCases;
    private List<Long> caseIds;
}
