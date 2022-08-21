package com.shtand.aggregator.task.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedCaseDto {
    private String id;
    private String errorCode;
    private String providerName;
    private Set<String> productsAffected;
    private int numberOfSupportCases;
    private List<Long> caseIds;
    private String caseStatus;
}
