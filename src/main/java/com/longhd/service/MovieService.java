package com.longhd.service;

import com.longhd.dao.MovieDao;
import com.longhd.exception.DataNotFoundException;
import com.longhd.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public List<Movie> getMovies() {
        return movieDao.getMovies();
    }

    public void addMovie(Movie movie) {
        if (movieDao.isExists(movie.name())) {
            throw new IllegalStateException("Movie existed.");
        }
        int result = movieDao.insertMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong.");
        }
    }

    public void deleteMovie(int id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
            int result = movieDao.deleteMovie(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete movie");
            }
        }, () -> {
           throw new DataNotFoundException(String.format("Movie with id %s not found.", id));
        });

    }

    public Movie getMovieById(int id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format("Movie with id %s not found.", id)));
    }
}
