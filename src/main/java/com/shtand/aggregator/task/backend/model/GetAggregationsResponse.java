package com.shtand.aggregator.task.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetAggregationsResponse {

    private List<AggregatedCaseDto> aggregatedCaseDtos;
}
