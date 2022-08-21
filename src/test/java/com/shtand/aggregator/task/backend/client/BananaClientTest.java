package com.shtand.aggregator.task.backend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shtand.aggregator.task.backend.model.BananaCaseList;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ExtendWith(SpringExtension.class)
@RestClientTest(BananaClient.class)
class BananaClientTest {

    @Autowired
    private BananaClient bananaClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void name() throws JsonProcessingException {
        BananaCaseList bananaCaseList = new BananaCaseList();
        BaseCase case1 = BaseCase.builder().caseId(1L).build();
        bananaCaseList.setData(new BaseCase[]{case1});
        server.expect(requestTo("/homeassignment/banana")).
                andRespond(withSuccess(objectMapper.writeValueAsString(bananaCaseList), MediaType.APPLICATION_JSON));
        BananaCaseList cases = bananaClient.getCases();
        Assertions.assertThat(cases.getData()[0]).isEqualTo(case1);
    }
}