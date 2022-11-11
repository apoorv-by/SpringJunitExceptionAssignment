package com.spring.junit.exception.SpringJunitException.controller;

import com.spring.junit.exception.SpringJunitException.exceptionHandling.MovieException;
import com.spring.junit.exception.SpringJunitException.model.Movie;
import com.spring.junit.exception.SpringJunitException.model.Response;
import com.spring.junit.exception.SpringJunitException.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @RequestMapping(value = "/get-movie", method = RequestMethod.GET)
    public ResponseEntity<List<Movie> > getMovie(){
        return new ResponseEntity<>(movieService.getMovie(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-movie-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") ObjectId id) throws MovieException {

        Movie movie = movieService.getMovieById(id);

        if(movie == null){
            throw new MovieException("Movie doesn't exist");
        }

        return new ResponseEntity<Movie>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-movie", method = RequestMethod.POST)
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie payload) throws MovieException
    {
        if(payload.getName() == null)
        {
            throw new MovieException("Please enter movie name");
        }

        if(payload.getReleaseDate() == null)
        {
            throw new MovieException("Release date of movie can't be null");
        }

        return new ResponseEntity<>(movieService.saveMovie(payload), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-movie/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Response> deleteMovie(@PathVariable("id") ObjectId id) throws MovieException
    {
        Movie emp = movieService.getMovieById(id);

        if(emp == null ){
            throw new MovieException("Movie doesn't exist");
        }

        movieService.removeMovie(id);

        return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Successfully deleted movie"), HttpStatus.OK);
    }

    @RequestMapping(value = "/update-movie/{id}", method = RequestMethod.PUT)
    public Movie updateMovie(@PathVariable("id") ObjectId id, @RequestBody Movie movie ) throws MovieException{

        Movie searchMovie = movieService.getMovieById(id);

        if(searchMovie == null){
            throw new MovieException("Movie doesn't exist");
        }

        else {
            searchMovie.setName(movie.getName());
            searchMovie.setReleaseDate(movie.getReleaseDate());
            return movieService.saveMovie(searchMovie);
        }
    }
}
