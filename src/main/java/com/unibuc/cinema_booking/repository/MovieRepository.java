package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.dto.SeatsForAMovieRequest;
import com.unibuc.cinema_booking.dto.SeatsForAMovieRespons;
import com.unibuc.cinema_booking.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepository implements IDaoRepository<Movie> {

    final private String table_movies = "Movies";
    final private String column_id = "ID_Movie";
    final private String column_name = "Name";
    final private String column_duration = "Duration";
    final private String column_is_3d = "IS_3D";
    final private String column_type = "Type";
    final private String column_rating = "Rating";
    final private String column_description = "Description";
    final private String column_release_year = "Release_Year";

    private JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Movie create(Movie movie) {
        String sql = String.format("insert into %s values (?, ?, ?, ?, ?, ?, ?, ?)",table_movies);
        System.out.println(movie.getType().toString());
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, movie.getName());
            preparedStatement.setInt(3, movie.getDurationMinutes());
            preparedStatement.setBoolean(4, movie.getIs3D());
            preparedStatement.setString(5, movie.getType().toString());
            preparedStatement.setInt(6, movie.getRating());
            preparedStatement.setString(7, movie.getDescription());
            preparedStatement.setInt(8,movie.getReleaseYear());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        movie.setId(generatedKeyHolder.getKey().longValue());
        return movie;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_movies,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Movie> getOne(Long id) {
        String sql = String.format("select * from %s u where u.%s = ?",table_movies,column_id);

        RowMapper<Movie> mapper = (resultSet, rowNum) ->
                new Movie(resultSet.getLong(column_id),
                        resultSet.getString(column_name),
                        resultSet.getInt(column_duration),
                        resultSet.getBoolean(column_is_3d),
                        EMovieType.valueOf(resultSet.getString(column_type)),
                        resultSet.getInt(column_rating),
                        resultSet.getString(column_description),
                        resultSet.getInt(column_release_year));
        List<Movie> movies = jdbcTemplate.query(sql, mapper, id);
        if(!movies.isEmpty()) {
            return Optional.of(movies.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Movie> getAll() {
        String sql = String.format("select * from %s ", table_movies);

                RowMapper<Movie> mapper = (resultSet, rowNum) ->
                        new Movie(resultSet.getLong(column_id),
                                resultSet.getString(column_name),
                                resultSet.getInt(column_duration),
                                resultSet.getBoolean(column_is_3d),
                                EMovieType.valueOf(resultSet.getString(column_type)),
                                resultSet.getInt(column_rating),
                                resultSet.getString(column_description),
                                resultSet.getInt(column_release_year));

        List<Movie> movies = jdbcTemplate.query(sql, mapper);
        return movies;
    }

    public List<Movie> getAllMoviesFromACinema() {
        String sql = String.format("select * from %s WHERE ", table_movies);

        RowMapper<Movie> mapper = (resultSet, rowNum) ->
                new Movie(resultSet.getLong(column_id),
                        resultSet.getString(column_name),
                        resultSet.getInt(column_duration),
                        resultSet.getBoolean(column_is_3d),
                        EMovieType.valueOf(resultSet.getString(column_type)),
                        resultSet.getInt(column_rating),
                        resultSet.getString(column_description),
                        resultSet.getInt(column_release_year));

        List<Movie> movies = jdbcTemplate.query(sql, mapper);
        return movies;
    }

    //return the available seats with aditional informations from a showtimes of a specific movie;
    public List<SeatsForAMovieRespons> getSeatsFromAShowTime(SeatsForAMovieRequest request)
    {
        String sql = " SELECT s.ID_Seat, ms.ID_Movie_Showtime, c.Name as NameCinema, ch.Name as NameHall, ms.Date, s.Number_Seat, s.Column_Seat, s.Row_Seat, s.Is_Reserved_Seat\n" +
                " FROM Seats s JOIN Cinema_Halls ch ON s.Cinema_Halls_ID = ch.ID_Cinema_Halls \n" +
                " JOIN Movie_Showtimes ms ON ms.Cinema_Halls_ID = ch.ID_Cinema_Halls \n" +
                " JOIN Cinemas c ON ch.Cinemas_ID = c.ID_Cinema WHERE ms.Movies_ID = ? AND s.Is_Reserved_Seat = ? ";

        RowMapper<SeatsForAMovieRespons> mapper = (resultSet, rowNum) ->
                new SeatsForAMovieRespons
                        (resultSet.getLong("ID_Seat"),
                                resultSet.getLong("ID_Movie_Showtime"),
                                resultSet.getString("NameCinema"),
                                resultSet.getString("NameHall"),
                                resultSet.getString("Date"),
                                resultSet.getInt("Number_Seat"),
                                resultSet.getInt("Column_Seat"),
                                resultSet.getInt("Row_Seat"),
                                resultSet.getBoolean("Is_Reserved_Seat"));

        List<SeatsForAMovieRespons> availableSeats = jdbcTemplate.query(sql, mapper, request.getIdMovie(), !request.getAvailable());
        return availableSeats;
    }

}
