package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.exception.MethodNotImplementedException;
import com.unibuc.cinema_booking.model.Cinema;
import com.unibuc.cinema_booking.model.Seat;
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
public class SeatRepository implements IDaoRepository<Seat> {

    final private String table_seats = "Seats";
    final private String column_id = "ID_Seat";
    final private String column_hall_cinema_id = "Cinema_Halls_ID";
    final private String column_number_seats = "Number_Seat";
    final private String column_column_seat = "Column_Seat";
    final private String column_row_seat = "Row_Seat";
    final private String column_is_reserved_seat = "Is_Reserved_Seat";

    private JdbcTemplate jdbcTemplate;

    public SeatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Seat create(Seat seat) {
        String sql = String.format("insert into %s values (?, ?, ?, ?, ?, ?)", table_seats);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, seat.getCinemaHallId());
            preparedStatement.setInt(3, seat.getNumberSeat());
            preparedStatement.setInt(4, seat.getColumnSeat());
            preparedStatement.setInt(5, seat.getRowSeat());
            preparedStatement.setBoolean(6, seat.getIsReserved());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        seat.setId(generatedKeyHolder.getKey().longValue());
        return seat;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_seats,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Seat> getOne(Long id) {
        String sql = String.format("select * from %s s where s.%s = ?",table_seats,column_id);

        RowMapper<Seat> mapper = (resultSet, rowNum) ->
                new Seat(resultSet.getLong(column_id),
                        resultSet.getLong(column_hall_cinema_id),
                        resultSet.getInt(column_number_seats),
                        resultSet.getInt(column_row_seat),
                        resultSet.getInt(column_column_seat),
                        resultSet.getBoolean(column_is_reserved_seat));


        List<Seat> seats = jdbcTemplate.query(sql, mapper, id);
        if(!seats.isEmpty()) {
            return Optional.of(seats.get(0));
        } else {
            return Optional.empty();
        }

    }
    @Override
    public List<Seat> getAll() {
        String sql = String.format("select * from %s",table_seats);

        RowMapper<Seat> mapper = (resultSet, rowNum) ->
                new Seat(resultSet.getLong(column_id),
                        resultSet.getLong(column_hall_cinema_id),
                        resultSet.getInt(column_number_seats),
                        resultSet.getInt(column_row_seat),
                        resultSet.getInt(column_column_seat),
                        resultSet.getBoolean(column_is_reserved_seat));

        List<Seat> seats = jdbcTemplate.query(sql, mapper);
        return seats;
    }

    public Optional<Seat> getOneByIDHallsAndNumber(Long id, int numberSeat) {
        String sql = String.format("select * from %s s where s.%s = ? and s.%s = ?",table_seats,column_hall_cinema_id,column_number_seats);

        RowMapper<Seat> mapper = (resultSet, rowNum) ->
                new Seat(resultSet.getLong(column_id),
                        resultSet.getLong(column_hall_cinema_id),
                        resultSet.getInt(column_number_seats),
                        resultSet.getInt(column_row_seat),
                        resultSet.getInt(column_column_seat),
                        resultSet.getBoolean(column_is_reserved_seat));


        List<Seat> seats = jdbcTemplate.query(sql, mapper, id, numberSeat);
        if(!seats.isEmpty()) {
            return Optional.of(seats.get(0));
        } else {
            return Optional.empty();
        }

    }
    public int setSeatReservedFree(long idSeat, Boolean value)
    {
        String sql = String.format("update %s u set u.%s = ? where u.%s = ?",table_seats,column_is_reserved_seat,column_id);
        int numberOfUpdatedUsers = jdbcTemplate.update(sql, value, idSeat);
        return numberOfUpdatedUsers;
    }
}
