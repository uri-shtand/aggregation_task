package com.shtand.aggregator.task.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BananaCaseList {
    private BaseCase[] data;
}
