package com.longhd.dao.impl;

import com.longhd.dao.MovieDao;
import com.longhd.mapper.MovieRowMapper;
import com.longhd.model.Movie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDaoImpl implements MovieDao {

    @Qualifier("mysqlJdbcTemplate")
    private final JdbcTemplate mysqlJdbcTemplate;
    @Qualifier("postgresqlJdbcTemplate")
    private final JdbcTemplate postgresqlJdbcTemplate;

    public MovieDaoImpl(JdbcTemplate mysqlJdbcTemplate, JdbcTemplate postgresqlJdbcTemplate) {
        this.mysqlJdbcTemplate = mysqlJdbcTemplate;
        this.postgresqlJdbcTemplate = postgresqlJdbcTemplate;
    }

    @Override
    public List<Movie> getMovies() {
        String sql = """
                SELECT id, name, release_date
                FROM movie
                LIMIT 100;    
                """;
        return mysqlJdbcTemplate.query(sql, new MovieRowMapper());
    }

    @Override
    public int insertMovie(Movie movie) {
        String sql = """
                    INSERT INTO movie(name, release_date) 
                    VALUES (?, ?)
                    """;
        return mysqlJdbcTemplate.update(sql, movie.name(), movie.releaseDate());
    }

    @Override
    public int deleteMovie(int id) {
        String sql = """
                DELETE FROM movie
                WHERE id = ?
                """;
        return mysqlJdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        String sql = """
                SELECT id, name, release_date
                FROM movie
                WHERE id = (?);    
                """;
        return mysqlJdbcTemplate.query(sql, new MovieRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public boolean isExists(String name) {
        String sql = """
                SELECT id, name, release_date
                FROM movie
                WHERE name = (?);    
                """;
        List<Movie> movies = mysqlJdbcTemplate.query(sql, new MovieRowMapper(), name);
        return !movies.isEmpty();
    }
}
