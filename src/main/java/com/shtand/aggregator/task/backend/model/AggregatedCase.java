package com.shtand.aggregator.task.backend.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@Document
public class AggregatedCase {

    @Id
    private String id;

    private String errorCode;
    private String providerName;
    private Set<String> productsAffected;
    private int numberOfSupportCases;
    private List<Long> caseIds;
    private String caseStatus;
}
