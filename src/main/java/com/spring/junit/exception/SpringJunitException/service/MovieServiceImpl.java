package com.spring.junit.exception.SpringJunitException.service;

import com.spring.junit.exception.SpringJunitException.model.Movie;
import com.spring.junit.exception.SpringJunitException.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("movieService")
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public List<Movie> getMovie() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(ObjectId id) {
        Optional<Movie> tempMovie = movieRepository.findById(id);

        if(tempMovie.isPresent()) return tempMovie.get();

        return null;
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void removeMovie(ObjectId id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Movie updateMovie(Movie movie){
        return movieRepository.save(movie);
    }
}
