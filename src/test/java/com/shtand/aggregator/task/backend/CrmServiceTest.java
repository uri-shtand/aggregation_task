package com.shtand.aggregator.task.backend;

import com.google.common.collect.ImmutableList;
import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.BaseCase;
import com.shtand.aggregator.task.backend.model.RefreshResponse;
import com.shtand.aggregator.task.backend.reader.CrmAggregatorLogic;
import com.shtand.aggregator.task.backend.reader.CrmCaseFetchService;
import com.shtand.aggregator.task.backend.repo.AggregatedCaseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrmServiceTest {

    @Mock
    CrmAggregatorLogic crmAggregatorLogic;

    @Mock
    CrmCaseFetchService crmCaseFetchService;

    @Mock
    AggregatedCaseRepository aggregatedCaseRepository;

    @InjectMocks
    CrmService crmService;
    @Mock
    private BaseCase baseCase1;
    @Mock
    private AggregatedCase aggCase1;

    @Test
    void refresh() {
        when(crmCaseFetchService.fetchCases()).thenReturn(ImmutableList.of(baseCase1));
        when(crmAggregatorLogic.aggregateCases(ImmutableList.of(baseCase1))).thenReturn(ImmutableList.of(aggCase1));
        RefreshResponse refresh = crmService.refresh();
        verify(aggregatedCaseRepository).deleteAll();
        verify(aggregatedCaseRepository).saveAll(ImmutableList.of(aggCase1));
        Assertions.assertThat(refresh.getTotalAggregatedCaseCount()).isEqualTo(1);
    }
}