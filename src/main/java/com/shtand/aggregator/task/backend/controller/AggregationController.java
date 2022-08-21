package com.shtand.aggregator.task.backend.controller;

import com.shtand.aggregator.task.backend.CrmService;
import com.shtand.aggregator.task.backend.model.GetAggregationsResponse;
import com.shtand.aggregator.task.backend.model.RefreshResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "aggregation", produces = MediaType.APPLICATION_JSON_VALUE)
public class AggregationController {

    private CrmService crmService;

    public AggregationController(CrmService crmService) {
        this.crmService = crmService;
    }

    @GetMapping("/all")
    public GetAggregationsResponse getAggregations() {
        return new GetAggregationsResponse();
    }

    @PostMapping("/refresh")
    public RefreshResponse refresh() {
        return crmService.refresh();
    }
}
