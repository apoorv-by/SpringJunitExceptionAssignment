package com.spring.junit.exception.SpringJunitException.repository;

import com.spring.junit.exception.SpringJunitException.model.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("movieRepository")
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
}
