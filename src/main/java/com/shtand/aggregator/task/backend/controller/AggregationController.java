package com.shtand.aggregator.task.backend.controller;

import com.shtand.aggregator.task.backend.model.AggregatedCaseDto;
import com.shtand.aggregator.task.backend.model.GetAggregationsResponse;
import com.shtand.aggregator.task.backend.model.RefreshResponse;
import com.shtand.aggregator.task.backend.service.CrmService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "aggregation", produces = MediaType.APPLICATION_JSON_VALUE)
public class AggregationController {

    private CrmService crmService;

    public AggregationController(CrmService crmService) {
        this.crmService = crmService;
    }

    @GetMapping("/all")
    public GetAggregationsResponse getAggregations(@RequestParam(value = "providerName", required = false) String providerName,
                                                   @RequestParam(value = "caseStatus", required = false) String caseStatus) {
        List<AggregatedCaseDto> aggregatedCaseDtos = crmService.getAggregations(Optional.ofNullable(providerName), Optional.ofNullable(caseStatus));
        return GetAggregationsResponse.builder()
                .aggregatedCaseDtos(aggregatedCaseDtos)
                .build();
    }

    @PostMapping("/refresh")
    public RefreshResponse refresh() {
        return crmService.refreshIfNeeded();
    }
}
