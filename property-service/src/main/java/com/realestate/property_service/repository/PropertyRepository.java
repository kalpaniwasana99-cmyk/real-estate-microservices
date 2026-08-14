package com.realestate.property_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realestate.property_service.model.Property;

import java.util.List;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {
    // අවශ්‍ය නම් Owner ID එක මඟින් ප්‍රොපර්ටිස් සෙවීමට
    List<Property> findByOwnerId(String ownerId);
}