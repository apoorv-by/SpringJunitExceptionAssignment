package com.spring.junit.exception.SpringJunitException.service;

import com.spring.junit.exception.SpringJunitException.model.Movie;
import org.bson.types.ObjectId;

import java.util.List;

public interface MovieService {

    List<Movie> getMovie();

    Movie getMovieById(ObjectId id);

    Movie saveMovie(Movie movie);

    void removeMovie(ObjectId id);

    Movie updateMovie(Movie movie);

}
