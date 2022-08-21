package com.shtand.aggregator.task.backend.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class AggregatedCase {

    @Id
    private String id;

    private String errorCode;
    private String providerName;
    private Set<String> productsAffected;
    private int numberOfSupportCases;
    private List<Long> caseIds;
}
