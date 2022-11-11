package com.spring.junit.exception.SpringJunitException.service;

import com.spring.junit.exception.SpringJunitException.model.Movie;
import com.spring.junit.exception.SpringJunitException.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieServiceTest {

    //dummy data
    @Mock
    private MovieRepository repository;

    //dummy service for injecting data
    @InjectMocks
    private MovieServiceImpl service;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllEmployee(){
        List<Movie> movie = new ArrayList<>();
        movie.add(new Movie(new ObjectId("636bab81c33b70379d775036"),"Movie", "2022"));
        movie.add(new Movie(new ObjectId("636bab81c33b70379d775037"),"abcd","2018"));

        when(repository.findAll()).thenReturn(movie);

        List<Movie> employeesResult = service.getMovie();

        assertEquals(2, employeesResult.size());
    }

    @Test
    public void getEmployeeById(){

        Movie movie = new Movie(new ObjectId("636bab81c33b70379d775036"),"Movie", "2022");

        ObjectId id = new ObjectId("636bab81c33b70379d775036");
        when(repository.findById(id)).thenReturn(Optional.of(movie));

        Movie movieResult = service.getMovieById(id);

        assertEquals(id, movieResult.getId());
        assertEquals("Movie", movieResult.getName());
    }

    @Test
    public void saveEmployee(){

        Movie movie = new Movie(new ObjectId("636bab81c33b70379d775036"),"Movie", "2022");

        ObjectId id = new ObjectId("636bab81c33b70379d775036");
        when(repository.save(movie)).thenReturn(movie);

        Movie movieResult = service.saveMovie(movie);

        assertEquals(id, movieResult.getId());
        assertEquals("Movie", movieResult.getName());
    }

    @Test
    public void deleteEmployeeById(){

        ObjectId id = new ObjectId("636bab81c33b70379d775036");
        Movie movie = new Movie(id,"Movie", "2022");

        service.removeMovie(id);

        verify(repository, times(1)).deleteById(movie.getId());

    }

}
