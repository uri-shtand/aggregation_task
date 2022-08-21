package com.shtand.aggregator.task.backend.client;

import com.shtand.aggregator.task.backend.model.BananaCaseList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BananaClient {

    public static final String HOMEASSIGNMENT_BANANA = "/homeassignment/banana";
    private final String baseUrl;
    private RestTemplate restTemplate;

    public BananaClient(@Value("${banana.baseUrl}") String baseUrl,
                        RestTemplateBuilder restTemplateBuilder) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    public BananaCaseList getCases() {
        return restTemplate.getForObject(HOMEASSIGNMENT_BANANA,BananaCaseList.class);
    }
}
