package com.shtand.aggregator.task.backend.reader;

import com.google.common.collect.ImmutableList;
import com.shtand.aggregator.task.backend.model.AggregatedCase;
import com.shtand.aggregator.task.backend.model.BaseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrmAggregatorLogicTest {

    @InjectMocks
    CrmAggregatorLogic crmAggregatorLogic;
    private BaseCase baseCase1 = BaseCase.builder()
            .caseId(1L)
            .createdErrorCode(1L)
            .providerId(100L)
            .build();
    private BaseCase baseCase2 = BaseCase.builder()
            .caseId(2L)
            .createdErrorCode(2L)
            .providerId(100L)
            .build();
    private BaseCase baseCase3 = BaseCase.builder()
            .caseId(3L)
            .createdErrorCode(2L)
            .providerId(100L)
            .build();
    private BaseCase baseCase4 = BaseCase.builder()
            .caseId(4L)
            .createdErrorCode(2L)
            .providerId(200L)
            .build();

    @Mock
    private AggregatedCaseFactory aggregatedCaseFactory;
    private AggregatedCase aggregatedCase1 = AggregatedCase.builder().id("1").build();
    private AggregatedCase aggregatedCase2 = AggregatedCase.builder().id("2").build();
    private AggregatedCase aggregatedCase3 = AggregatedCase.builder().id("2").build();
    private AggregatedCase aggregatedCase4 = AggregatedCase.builder().id("4").build();


    @Test
    void aggregateCases() {
        List<BaseCase> baseCases = ImmutableList.of(baseCase1, baseCase2, baseCase3,baseCase4);
        doReturn(aggregatedCase1).when(aggregatedCaseFactory).createAggregatedCase(baseCase1);
        doReturn(aggregatedCase2).when(aggregatedCaseFactory).createAggregatedCase(baseCase2);
        doReturn(aggregatedCase3).when(aggregatedCaseFactory).createAggregatedCase(baseCase3);
        doReturn(aggregatedCase4).when(aggregatedCaseFactory).createAggregatedCase(baseCase4);
        when(aggregatedCaseFactory.addCase(aggregatedCase2,aggregatedCase3)).thenReturn(aggregatedCase2);
        List<AggregatedCase> aggregatedCases = crmAggregatorLogic.aggregateCases(baseCases);
        Assertions.assertThat(aggregatedCases).containsExactlyInAnyOrder(aggregatedCase1,aggregatedCase2,aggregatedCase4);
    }
}