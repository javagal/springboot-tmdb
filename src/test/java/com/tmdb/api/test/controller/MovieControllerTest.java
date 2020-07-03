package com.tmdb.api.test.controller;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.tmdb.api.controller.MovieController;
import com.tmdb.api.jsontopojo.Movies;
import com.tmdb.api.jsontopojo.Result;
import static org.assertj.core.api.Assertions.*;
@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
public class MovieControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieControllerTest.class);
	@MockBean
	private MovieController movieController;
	
	private RestTemplate restTemplate;

    private String URL;

    @Before
    public void getRestTemplate(){
        this.restTemplate = new RestTemplate();
        this.URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=8f2d9ea65e193ea40b4f8d7f9ca25c1b&language=en-US&page=1";
    }
	
    @Test
	public void getAllMoviesTest() {
		String expected="Eurovision Song Contest: The Story of Fire Saga";
		 final String url = URL;
		 Movies movies = restTemplate.getForObject(url,Movies.class);
		 assertThat(movies.getPage()).isEqualTo(1);
		
		 Result result = movies.getResults().get(0);
		  System.out.println(result.getOriginalTitle());
		  assertThat(result.getOriginalTitle()).isEqualTo(expected);
	}
	
	
}
