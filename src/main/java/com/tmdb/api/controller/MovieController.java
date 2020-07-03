package com.tmdb.api.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmdb.api.jsontopojo.Movies;
import com.tmdb.api.jsontopojo.Result;

@RestController
@RequestMapping("/bookmyshow")
public class MovieController {

	@Autowired
	private Environment env;
	
	

	@GetMapping("/allMovies")
	public List<Result> getAllMovies() {
		String allMoviesURL = env.getProperty("REST_API_URL_WITH_KEY_AND_ALL_MOIVES");
		RestTemplate restTemplate = new RestTemplate();
		Movies allMovies = restTemplate.getForObject(allMoviesURL, Movies.class);
		List<Result> results = allMovies.getResults();
		return results;

	}
	
	@GetMapping("/getByPopularity")
	public List<Result> getPopularMovies(){
		String allTopRatedMoviesURL = env.getProperty("REST_API_URL_WITH_KEY_AND_TOP_RATED");
		RestTemplate restTemplate = new RestTemplate();
		Movies allPopularMovies = restTemplate.getForObject(allTopRatedMoviesURL, Movies.class);
			List<Result> results = allPopularMovies.getResults();
			Collections.sort(results, new Comparator<Result>() {
				@Override
				public int compare(Result o1, Result o2) {
					return (int) (o2.getPopularity()-o1.getPopularity());
				}
			});
		return results;
	}
	
	
	@GetMapping("/getByGenre/{genre_id}")
	public List<Result> getMoviesByGenres(@PathVariable("genre_id") int genre_id[]){
		String allTopRatedMoviesURL = env.getProperty("REST_API_URL_WITH_KEY_AND_GENRE");
		RestTemplate restTemplate = new RestTemplate();
		Movies genrerMovies = restTemplate.getForObject(allTopRatedMoviesURL, Movies.class);
			List<Result> results = genrerMovies.getResults();
			System.out.println(results.toString());
		return results;
	}
	@GetMapping("/getByAverageVoteRating/{vote_rate}")
	public List<Result> getMoviesByAverageRating(@PathVariable("vote_rate") int vote_rate){
		String allTopRatedMoviesURL = env.getProperty("REST_API_URL_WITH_KEY_AND_VOTE_AVERAGES");
		RestTemplate restTemplate = new RestTemplate();
		Movies genrerMovies = restTemplate.getForObject(allTopRatedMoviesURL, Movies.class);
			List<Result> results = genrerMovies.getResults();
			results.stream().filter(movie -> movie.getVoteAverage() >= vote_rate).collect(Collectors.toList());
			System.out.println(results.toString());
		return results;
	}
}
