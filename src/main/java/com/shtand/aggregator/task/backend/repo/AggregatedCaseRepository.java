package com.shtand.aggregator.task.backend.repo;

import com.shtand.aggregator.task.backend.model.AggregatedCase;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregatedCaseRepository extends MongoRepository<AggregatedCase,String> {
}
