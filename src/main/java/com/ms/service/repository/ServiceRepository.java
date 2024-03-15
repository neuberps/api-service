package com.ms.service.repository;

import com.ms.service.model.ServiceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ServiceRepository extends MongoRepository<ServiceModel, String> {
    Optional<ServiceModel> findByName(String name);
}
