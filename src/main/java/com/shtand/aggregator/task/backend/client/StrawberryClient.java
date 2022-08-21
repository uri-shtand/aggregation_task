package com.shtand.aggregator.task.backend.client;

import com.shtand.aggregator.task.backend.model.BananaCaseList;
import com.shtand.aggregator.task.backend.model.StrawberryCaseList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StrawberryClient {

    public static final String HOMEASSIGNMENT_STRAWBERRY = "/homeassignment/strawberry";
    private final String baseUrl;
    private RestTemplate restTemplate;

    public StrawberryClient(@Value("${strawberry.baseUrl}") String baseUrl,
                            RestTemplateBuilder restTemplateBuilder) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    public StrawberryCaseList getCases() {
        return restTemplate.getForObject(HOMEASSIGNMENT_STRAWBERRY,StrawberryCaseList.class);
    }
}
