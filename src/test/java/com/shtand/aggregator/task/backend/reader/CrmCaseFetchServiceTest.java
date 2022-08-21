package com.shtand.aggregator.task.backend.reader;

import com.google.common.collect.ImmutableList;
import com.shtand.aggregator.task.backend.client.CrmClient;
import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrmCaseFetchServiceTest {

    CrmCaseFetchService crmCaseFetchService;

    @Mock
    CrmClient client1;

    @Mock
    CrmClient client2;

    @BeforeEach
    void setUp() {
        crmCaseFetchService = new CrmCaseFetchService(ImmutableList.of(client1,client2));
    }

    @Test
    void fetchCases() {
        BaseCase baseCase1 = BaseCase.builder().build();
        BaseCase baseCase2 = BaseCase.builder().build();
        BaseCase baseCase3 = BaseCase.builder().build();
        when(client1.getCases()).thenReturn(ImmutableList.of(baseCase1,baseCase2));
        when(client2.getCases()).thenReturn(ImmutableList.of(baseCase2,baseCase3));
        List<BaseCase> baseCases = crmCaseFetchService.fetchCases();
        Assertions.assertThat(baseCases).containsExactly(baseCase1,baseCase2,baseCase2,baseCase3);
    }
}