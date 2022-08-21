package com.shtand.aggregator.task.backend;

import com.shtand.aggregator.task.backend.model.GetAggregationsResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

	public static final String CASEAGG_AGGREGATIONS_ALL = "/caseagg/aggregation/all";
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void getAggregations() throws Exception {
		String baseUrl = "http://localhost:" + port;
		ResponseEntity<GetAggregationsResponse> aggregationsResultResponseEntity = restTemplate.getForEntity(baseUrl + CASEAGG_AGGREGATIONS_ALL, GetAggregationsResponse.class);
		Assertions.assertThat(aggregationsResultResponseEntity.getStatusCodeValue()).isEqualTo(200);
		GetAggregationsResponse aggregationsResultResponseEntityBody = aggregationsResultResponseEntity.getBody();
		Assertions.assertThat(aggregationsResultResponseEntityBody).isNotNull();
	}

}
