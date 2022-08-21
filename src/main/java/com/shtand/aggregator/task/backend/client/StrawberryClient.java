package com.shtand.aggregator.task.backend.client;

import com.shtand.aggregator.task.backend.model.BananaCaseList;
import com.shtand.aggregator.task.backend.model.BaseCase;
import com.shtand.aggregator.task.backend.model.StrawberryCaseList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class StrawberryClient implements CrmClient {

    public static final String HOMEASSIGNMENT_STRAWBERRY = "/homeassignment/strawberry";
    private final String baseUrl;
    private RestTemplate restTemplate;

    public StrawberryClient(@Value("${strawberry.baseUrl}") String baseUrl,
                            RestTemplateBuilder restTemplateBuilder) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    @Override
    public List<BaseCase> getCases() {
        StrawberryCaseList strawberryCaseList = restTemplate.getForObject(HOMEASSIGNMENT_STRAWBERRY, StrawberryCaseList.class);
        return Arrays.asList(strawberryCaseList.getData());
    }
}
