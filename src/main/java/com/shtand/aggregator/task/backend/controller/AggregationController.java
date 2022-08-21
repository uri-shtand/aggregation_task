package com.shtand.aggregator.task.backend.controller;

import com.shtand.aggregator.task.backend.model.GetAggregationsResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "aggregation", produces = MediaType.APPLICATION_JSON_VALUE)
public class AggregationController {


    @GetMapping(value = "/all")
    public GetAggregationsResponse getAggregations() {
        return new GetAggregationsResponse();
    }
}
