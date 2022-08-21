package com.shtand.aggregator.task.backend;

import com.google.common.collect.ImmutableList;
import com.shtand.aggregator.task.backend.model.BaseCase;
import com.shtand.aggregator.task.backend.model.GetAggregationsResponse;
import com.shtand.aggregator.task.backend.model.RefreshResponse;
import com.shtand.aggregator.task.backend.reader.CrmCaseFetchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

    public static final String CASEAGG_AGGREGATIONS_ALL = "/caseagg/aggregation/all";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CrmCaseFetchService crmCaseFetchService;
    private BaseCase baseCase1 = BaseCase.builder()
            .caseId(1L)
            .providerId(1L)
            .createdErrorCode(100L)
            .build();
	private BaseCase baseCase2 = BaseCase.builder()
			.caseId(2L)
			.providerId(2L)
			.createdErrorCode(200L)
			.build();

    @Test
    void contextLoads() {
    }

    @Test
    public void refresh() {
        Mockito.when(crmCaseFetchService.fetchCases()).thenReturn(ImmutableList.of(baseCase1, baseCase2));
        String url = "http://localhost:" + port + "/caseagg/aggregation/refresh";
        ResponseEntity<RefreshResponse> refreshResponseResponseEntity = restTemplate.postForEntity(url, HttpEntity.EMPTY, RefreshResponse.class);
        assertThat(refreshResponseResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(refreshResponseResponseEntity.getBody().getTotalAggregatedCaseCount()).isEqualTo(2);

    }

    @Test
    public void getAggregations() throws Exception {
        String baseUrl = "http://localhost:" + port;
        ResponseEntity<GetAggregationsResponse> aggregationsResultResponseEntity = restTemplate.getForEntity(baseUrl + CASEAGG_AGGREGATIONS_ALL, GetAggregationsResponse.class);
        assertThat(aggregationsResultResponseEntity.getStatusCodeValue()).isEqualTo(200);
        GetAggregationsResponse aggregationsResultResponseEntityBody = aggregationsResultResponseEntity.getBody();
        assertThat(aggregationsResultResponseEntityBody).isNotNull();
    }

}
