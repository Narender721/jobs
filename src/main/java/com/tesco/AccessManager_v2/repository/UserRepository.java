package com.tesco.AccessManager_v2.repository;

import com.tesco.AccessManager_v2.model.UserModel;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCouchbaseRepository<UserModel,Integer> {
    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND dept.deptId = $1")
    Mono<UserModel> findUsersByUnitId(int unitId);
}
