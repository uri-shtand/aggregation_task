package com.shtand.aggregator.task.backend.repo;

import com.shtand.aggregator.task.backend.model.AggregatedCase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AggregatedCaseCustomRepository {

    private final MongoTemplate mongoTemplate;

    public AggregatedCaseCustomRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AggregatedCase> findAggregatedCases(Optional<String> providerName, Optional<String> caseStatus) {
        final Query query = new Query();
        final List<Criteria> filterCriteria = new ArrayList<>();
        providerName.ifPresent(value -> filterCriteria.add(Criteria.where("providerName").is(value)));
        caseStatus.ifPresent(value -> filterCriteria.add(Criteria.where("caseStatus").is(value)));
        if (!filterCriteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(filterCriteria));
        }
        return mongoTemplate.find(query, AggregatedCase.class);
    }
}
