package com.shtand.aggregator.task.backend.reader;

import com.shtand.aggregator.task.backend.client.CrmClient;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CrmCaseFetchService {

    private final List<CrmClient> crmClients;

    public CrmCaseFetchService(List<CrmClient> crmClients) {
        this.crmClients = crmClients;
    }

    public List<BaseCase> fetchCases() {
        return crmClients.stream().map(crmClient -> crmClient.getCases())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
