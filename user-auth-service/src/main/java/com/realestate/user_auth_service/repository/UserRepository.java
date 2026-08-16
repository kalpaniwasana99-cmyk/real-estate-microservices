package com.realestate.user_auth_service.repository;

import com.realestate.user_auth_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


Optional<User> findByEmail(String email);
}