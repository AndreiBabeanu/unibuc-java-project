package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.model.CinemaHall;
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
public class CinemaHallRepository implements IDaoRepository<CinemaHall> {

    final private String table_cinema_hall = "Cinema_Halls";
    final private String column_id = "ID_Cinema_Halls";
    final private String column_id_cinema = "Cinemas_ID";
    final private String column_name = "Name";
    final private String column_number_seats = "Number_seats";

    private JdbcTemplate jdbcTemplate;

    public CinemaHallRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CinemaHall create(CinemaHall cinemaHall) {
        String sql = String.format("INSERT INTO %s VALUES(?,?,?,?)",table_cinema_hall);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1,null);
            preparedStatement.setLong(2,cinemaHall.getCinemaId());
            preparedStatement.setString(3,cinemaHall.getName());
            preparedStatement.setLong(4,cinemaHall.getNumberSeats());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator,generatedKeyHolder);

        cinemaHall.setId(generatedKeyHolder.getKey().longValue());
        return cinemaHall;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_cinema_hall,column_id);
        Object[] args = new  Object[] {id};

        return jdbcTemplate.update(sql,args) == 1;
    }

    @Override
    public Optional<CinemaHall> getOne(Long id) {
        String sql = String.format("select * from %s l where l.%s = ?",table_cinema_hall,column_id);

        RowMapper<CinemaHall> mapper = (resultSet, rowNum) ->
                new CinemaHall(resultSet.getLong(column_id),
                        resultSet.getLong(column_id_cinema),
                        resultSet.getString(column_name),
                        resultSet.getLong(column_number_seats));


        List<CinemaHall> halls = jdbcTemplate.query(sql, mapper, id);
        if(!halls.isEmpty()) {
            return Optional.of(halls.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CinemaHall> getAll() {

        String sql = String.format("SELECT * FROM %s",table_cinema_hall);
        RowMapper<CinemaHall> mapper = (resultSet, rowNum) ->
                new CinemaHall(resultSet.getLong(column_id),
                        resultSet.getLong(column_id_cinema),
                        resultSet.getString(column_name),
                        resultSet.getLong(column_number_seats));

        List<CinemaHall> cinemaHalls = jdbcTemplate.query(sql,mapper);
        return cinemaHalls;
    }
}
