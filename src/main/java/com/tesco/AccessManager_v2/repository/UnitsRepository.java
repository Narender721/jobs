package com.tesco.AccessManager_v2.repository;

import com.tesco.AccessManager_v2.model.UnitsModel;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface UnitsRepository extends ReactiveCouchbaseRepository<UnitsModel, Integer> {
}
