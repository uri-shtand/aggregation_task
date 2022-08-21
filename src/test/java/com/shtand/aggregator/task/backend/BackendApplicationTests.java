package com.shtand.aggregator.task.backend;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.shtand.aggregator.task.backend.model.*;
import com.shtand.aggregator.task.backend.reader.CrmCaseFetchService;
import com.shtand.aggregator.task.backend.repo.AggregatedCaseRepository;
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

    @Autowired
    private AggregatedCaseRepository aggregatedCaseRepository;

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

    AggregatedCase aggregatedCase = AggregatedCase.builder()
            .caseStatus("test")
            .id("123")
            .numberOfSupportCases(1)
            .caseIds(ImmutableList.of(1234L))
            .errorCode("555")
            .providerName("testProvider")
            .productsAffected(ImmutableSet.of("testProduct"))
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
        aggregatedCaseRepository.deleteAll();
        aggregatedCaseRepository.saveAll(ImmutableList.of(aggregatedCase));
        String baseUrl = "http://localhost:" + port;
        ResponseEntity<GetAggregationsResponse> aggregationsResultResponseEntity = restTemplate.getForEntity(baseUrl + CASEAGG_AGGREGATIONS_ALL, GetAggregationsResponse.class);
        assertThat(aggregationsResultResponseEntity.getStatusCodeValue()).isEqualTo(200);
        GetAggregationsResponse aggregationsResultResponseEntityBody = aggregationsResultResponseEntity.getBody();
        assertThat(aggregationsResultResponseEntityBody).isNotNull();
        AggregatedCaseDto aggregatedCaseDto = aggregationsResultResponseEntityBody.getAggregatedCaseDtos().get(0);
        assertThat(aggregatedCaseDto.getId()).isEqualTo("123");
    }

    @Test
    public void getAggregationsWithFilter_hasResult() throws Exception {
        aggregatedCaseRepository.deleteAll();
        aggregatedCaseRepository.saveAll(ImmutableList.of(aggregatedCase));
        String baseUrl = "http://localhost:" + port;
        ResponseEntity<GetAggregationsResponse> aggregationsResultResponseEntity = restTemplate.getForEntity(baseUrl + CASEAGG_AGGREGATIONS_ALL+"?providerName=testProvider", GetAggregationsResponse.class);
        assertThat(aggregationsResultResponseEntity.getStatusCodeValue()).isEqualTo(200);
        GetAggregationsResponse aggregationsResultResponseEntityBody = aggregationsResultResponseEntity.getBody();
        assertThat(aggregationsResultResponseEntityBody).isNotNull();
        AggregatedCaseDto aggregatedCaseDto = aggregationsResultResponseEntityBody.getAggregatedCaseDtos().get(0);
        assertThat(aggregatedCaseDto.getId()).isEqualTo("123");
    }

    @Test
    public void getAggregationsWithFilter_noResult() throws Exception {
        aggregatedCaseRepository.deleteAll();
        aggregatedCaseRepository.saveAll(ImmutableList.of(aggregatedCase));
        String baseUrl = "http://localhost:" + port;
        ResponseEntity<GetAggregationsResponse> aggregationsResultResponseEntity = restTemplate.getForEntity(baseUrl + CASEAGG_AGGREGATIONS_ALL+"?providerName=dummy", GetAggregationsResponse.class);
        assertThat(aggregationsResultResponseEntity.getStatusCodeValue()).isEqualTo(200);
        GetAggregationsResponse aggregationsResultResponseEntityBody = aggregationsResultResponseEntity.getBody();
        assertThat(aggregationsResultResponseEntityBody).isNotNull();
        assertThat(aggregationsResultResponseEntityBody.getAggregatedCaseDtos()).isEmpty();
    }

}
