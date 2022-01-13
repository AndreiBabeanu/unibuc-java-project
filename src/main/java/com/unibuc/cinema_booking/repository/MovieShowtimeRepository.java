package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.model.MovieShowtime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieShowtimeRepository implements IDaoRepository<MovieShowtime> {

    final private String table_movies_showtime = "Movie_Showtimes";
    final private String column_id = "ID_Movie_Showtime";
    final private String column_id_movie = "Movies_ID";
    final private String column_id_hall = "Cinema_Halls_ID";
    final private String column_date = "Date";

    private final JdbcTemplate jdbcTemplate;

    public MovieShowtimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MovieShowtime create(MovieShowtime movieShowtime) {
        String sql = String.format("insert into %s values (?, ?, ?, ?)", table_movies_showtime);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, movieShowtime.getMovieId());
            preparedStatement.setLong(3, movieShowtime.getCinemaHallId());
            preparedStatement.setString(4, movieShowtime.getDate());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        //set id-ul introdus pentru userul deja creat
        movieShowtime.setId(generatedKeyHolder.getKey().longValue());
        return movieShowtime;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_movies_showtime,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<MovieShowtime> getOne(Long id) {
        String sql = String.format("select * from %s l where l.%s = ?",table_movies_showtime,column_id);

        RowMapper<MovieShowtime> mapper = (resultSet, rowNum) ->
                new MovieShowtime(resultSet.getLong(column_id),
                        resultSet.getLong(column_id_movie),
                        resultSet.getLong(column_id_hall),
                        resultSet.getString(column_date));


        List<MovieShowtime> movieShowtimes = jdbcTemplate.query(sql, mapper, id);
        if(!movieShowtimes.isEmpty()) {
            return Optional.of(movieShowtimes.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MovieShowtime> getAll() {
        String sql = String.format("select * from %s",table_movies_showtime);

        RowMapper<MovieShowtime> mapper = (resultSet, rowNum) ->
                new MovieShowtime(resultSet.getLong(column_id),
                        resultSet.getLong(column_id_movie),
                        resultSet.getLong(column_id_hall),
                        resultSet.getString(column_date));

        List<MovieShowtime> movieShowtimes = jdbcTemplate.query(sql, mapper);
        return movieShowtimes;
    }
}
