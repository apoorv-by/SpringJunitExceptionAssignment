package com.spring.junit.exception.SpringJunitException.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.junit.exception.SpringJunitException.SpringJunitExceptionApplication;
import com.spring.junit.exception.SpringJunitException.model.Movie;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class) // for running this with junit4
@ContextConfiguration(classes = SpringJunitExceptionApplication.class)
@SpringBootTest // spring test
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // to execute the test methods in order (based on name)
public class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext; //autowired configuration

    private MockMvc mockMvc; // For controller based mocks (for web layer)

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyAllMovie() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/get-movie")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }


    @Test
    public void verifyGetById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/get-movie-by-id/636bab81c33b70379d775036")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.id").value("636bab81c33b70379d775036"))  // checking the expected value and expression value(output response)
                .andExpect(jsonPath("$.name").value("Movie"))
                .andDo(print());
    }


    // bad request -> post instead of get -> exception successful
    @Test
    public void verifySaveMovieException() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/save-movie")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("Wrong Syntax"))
                .andDo(print());
    }

    @Test
    public void verifySaveMovie() throws Exception{
        Movie movie = new Movie(new ObjectId("636bab81c33b70379d775036"),"Movie", "2022");

        mockMvc.perform(MockMvcRequestBuilders.post("/save-movie")
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("636bab81c33b70379d775036"))
                .andExpect(jsonPath("$.name").value("Movie"))
                .andDo(print());
    }

    //invalid ID check
    @Test
    public void verifyInvalidGetById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/get-movie-by-id/636bab81c33b70379d775140")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Movie doesn't exist"))
                .andDo(print());
    }

    @Test
    public void verifyUpdateMovie() throws Exception{
        Movie movie = new Movie(new ObjectId("636bab81c33b70379d775036"),"Movie", "2022");

        mockMvc.perform(MockMvcRequestBuilders.put("/update-movie/636bab81c33b70379d775036")
                        .content(asJsonString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("636bab81c33b70379d775036"))
                .andExpect(jsonPath("$.name").value("Movie"))
                .andDo(print());
    }

    @Test
    public void verifyDeleteById() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete-movie/636bab81c33b70379d775037")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Successfully deleted movie"))
                .andDo(print());
    }

}
