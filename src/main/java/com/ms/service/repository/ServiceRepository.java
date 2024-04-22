package com.ms.service.repository;

import com.ms.service.model.ServiceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<ServiceModel, String> {
    Optional<ServiceModel> findByName(String name);

    Optional<ServiceModel> findByCategory(String category);
}
