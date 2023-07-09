package com.longhd.dao;

import com.longhd.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    List<Movie> getMovies();
    int insertMovie(Movie movie);
    int deleteMovie(int id);
    Optional<Movie> selectMovieById(int id);
    boolean isExists(String name);
}
