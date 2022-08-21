package com.shtand.aggregator.task.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class GetAggregationsResponse {

    private List<CaseAggregation> caseAggregations;
}
