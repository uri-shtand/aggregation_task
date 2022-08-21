package com.shtand.aggregator.task.backend.client;

import com.shtand.aggregator.task.backend.model.BaseCase;

import java.util.List;

public interface CrmClient {

    List<BaseCase> getCases();

}
