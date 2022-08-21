package com.shtand.aggregator.task.backend.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BaseCaseTest {

    @Test
    void map_to_from() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        BaseCase testObject = BaseCase.builder()
                .caseId(1L)
                .createdErrorCode(2L)
                .customerId(3L)
                .lastModifiedDate("4/2/2019 8:00")
                .ticketCreationDate("4/2/2019 8:00")
                .productName("test")
                .providerId(4L)
                .status("testStatus")
                .build();
        String testObjectAsString = objectMapper.writeValueAsString(testObject);
        assertThat(testObjectAsString).contains("Case ID");
        BaseCase resultObject = objectMapper.readValue(testObjectAsString, BaseCase.class);
        assertThat(resultObject).isEqualTo(testObject);
    }
}