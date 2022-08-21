package com.shtand.aggregator.task.backend;

import com.google.common.collect.ImmutableList;
import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.AggregatedCaseDto;
import com.shtand.aggregator.task.backend.model.BaseCase;
import com.shtand.aggregator.task.backend.model.RefreshResponse;
import com.shtand.aggregator.task.backend.reader.CrmAggregatorLogic;
import com.shtand.aggregator.task.backend.reader.CrmCaseFetchService;
import com.shtand.aggregator.task.backend.repo.AggregatedCaseRepository;
import com.shtand.aggregator.task.backend.service.AggregatedCaseConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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

    @Mock
    private AggregatedCaseConverter aggregatedCaseConverter;
    @Mock
    private AggregatedCaseDto aggCase1Dto;

    @Test
    void refresh() {
        when(crmCaseFetchService.fetchCases()).thenReturn(ImmutableList.of(baseCase1));
        when(crmAggregatorLogic.aggregateCases(ImmutableList.of(baseCase1))).thenReturn(ImmutableList.of(aggCase1));
        RefreshResponse refresh = crmService.refreshIfNeeded();
        verify(aggregatedCaseRepository).deleteAll();
        verify(aggregatedCaseRepository).saveAll(ImmutableList.of(aggCase1));
        assertThat(refresh.getTotalAggregatedCaseCount()).isEqualTo(1);
        assertThat(refresh.getLastRefreshTimestamp()).isPositive();
        assertThat(refresh.isSkippedRefresh()).isFalse();
        RefreshResponse refresh2 = crmService.refreshIfNeeded();
        assertThat(refresh2.isSkippedRefresh()).isTrue();
    }

    @Test
    void getAll() {
        when(aggregatedCaseRepository.findAll()).thenReturn(ImmutableList.of(aggCase1));
        when(aggregatedCaseConverter.apply(aggCase1)).thenReturn(aggCase1Dto);
        List<AggregatedCaseDto> aggregations = crmService.getAggregations();
        assertThat(aggregations.get(0)).isEqualTo(aggCase1Dto);
    }
}