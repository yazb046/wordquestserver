package com.wordquest.server.imaginary;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ImageRepository extends MongoRepository<Image, String> {
}