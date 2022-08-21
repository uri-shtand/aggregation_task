package com.shtand.aggregator.task.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shtand.aggregator.task.backend.model.BananaCaseList;
import com.shtand.aggregator.task.backend.model.BaseCase;
import com.shtand.aggregator.task.backend.model.StrawberryCaseList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(StrawberryClient.class)
class StrawberryClientTest {

    @Autowired
    private StrawberryClient strawberryClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void successfulCase() throws JsonProcessingException {
        StrawberryCaseList strawberryCaseList = new StrawberryCaseList();
        BaseCase case1 = BaseCase.builder().caseId(1L).build();
        strawberryCaseList.setData(new BaseCase[]{case1});
        server.expect(requestTo("/homeassignment/strawberry")).
                andRespond(withSuccess(objectMapper.writeValueAsString(strawberryCaseList), MediaType.APPLICATION_JSON));
        List<BaseCase> cases = strawberryClient.getCases();
        Assertions.assertThat(cases.get(0)).isEqualTo(case1);
    }
}