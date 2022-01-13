package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.model.Location;
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
public class CinemaRepository implements IDaoRepository<Cinema> {

    final private String table_cinemas = "Cinemas";
    final private String column_id = "ID_Cinema";
    final private String column_id_location = "Locations_ID";
    final private String column_name = "Name";
    final private String column_phone_number = "Phone_Number";

    private JdbcTemplate jdbcTemplate;

    public CinemaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cinema create(Cinema cinema) {

        String sql = String.format("insert into %s values (?, ?, ?, ?)", table_cinemas);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, cinema.getLocationId());
            preparedStatement.setString(3, cinema.getName());
            preparedStatement.setString(4, cinema.getPhoneNumber());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        //set id-ul introdus pentru userul deja creat
        cinema.setId(generatedKeyHolder.getKey().longValue());
        return cinema;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_cinemas,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Cinema> getOne(Long id) {
        String sql = String.format("select * from %s l where l.%s = ?",table_cinemas,column_id);

        RowMapper<Cinema> mapper = (resultSet, rowNum) ->
                new Cinema(resultSet.getLong(column_id),
                        resultSet.getLong(column_id_location),
                        resultSet.getString(column_name),
                        resultSet.getString(column_phone_number));


        List<Cinema> cinemas = jdbcTemplate.query(sql, mapper, id);
        if(!cinemas.isEmpty()) {
            return Optional.of(cinemas.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Cinema> getAll() {
        String sql = String.format("select * from %s",table_cinemas);

        RowMapper<Cinema> mapper = (resultSet, rowNum) ->
                new Cinema(resultSet.getLong(column_id),
                        resultSet.getLong(column_id_location),
                        resultSet.getString(column_name),
                        resultSet.getString(column_phone_number));

        List<Cinema> cinemas = jdbcTemplate.query(sql, mapper);
        return cinemas;
    }
}
