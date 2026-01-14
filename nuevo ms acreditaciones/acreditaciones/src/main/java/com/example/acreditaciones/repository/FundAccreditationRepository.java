package com.example.acreditaciones.repository;

import com.example.acreditaciones.model.FundAccreditation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FundAccreditationRepository extends MongoRepository<FundAccreditation, ObjectId> {
}

