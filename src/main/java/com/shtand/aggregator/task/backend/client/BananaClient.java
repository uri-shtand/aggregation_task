package com.shtand.aggregator.task.backend.client;

import com.shtand.aggregator.task.backend.model.BananaCaseList;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class BananaClient implements CrmClient {

    public static final String HOMEASSIGNMENT_BANANA = "/homeassignment/banana";
    private final String baseUrl;
    private RestTemplate restTemplate;

    public BananaClient(@Value("${banana.baseUrl}") String baseUrl,
                        RestTemplateBuilder restTemplateBuilder) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    @Override
    public List<BaseCase> getCases() {
        BananaCaseList bananaCaseList = restTemplate.getForObject(HOMEASSIGNMENT_BANANA, BananaCaseList.class);
        return Arrays.asList(bananaCaseList.getData());
    }
}
