package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.dto.ReservationsForUserResponse;
import com.unibuc.cinema_booking.model.Location;
import com.unibuc.cinema_booking.model.Reservation;
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
public class ReservationRepository implements IDaoRepository<Reservation>{

    final private String table_reservation = "Reservations";
    final private String column_id = "ID_Reservation";
    final private String column_reviews_id = "Reviews_ID";
    final private String column_users_id = "Users_ID";
    final private String column_ticket_id = "Tickets_ID";
    final private String column_confirmed = "Confirmed";

    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Reservation create(Reservation reservation) {
        String sql = String.format("insert into %s values (?, ?, ?, ?, ?)", table_reservation);
        System.out.println(reservation.toString());
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setObject(2,null);
            preparedStatement.setLong(3, reservation.getUserId());
            preparedStatement.setLong(4, reservation.getTicketId());
            preparedStatement.setBoolean(5, reservation.isConfirmed());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        reservation.setId(generatedKeyHolder.getKey().longValue());
        return reservation;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_reservation,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Reservation> getOne(Long id) {
        String sql = String.format("select * from %s l where l.%s = ?",table_reservation,column_id);

        RowMapper<Reservation> mapper = (resultSet, rowNum) ->
                new Reservation(resultSet.getLong(column_id),
                        resultSet.getLong(column_reviews_id),
                        resultSet.getLong(column_users_id),
                        resultSet.getLong(column_ticket_id),
                        resultSet.getBoolean(column_confirmed));


        List<Reservation> reservations = jdbcTemplate.query(sql, mapper, id);
        if(!reservations.isEmpty()) {
            return Optional.of(reservations.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> getAll() {
        String sql = String.format("select * from %s",table_reservation);

        RowMapper<Reservation> mapper = (resultSet, rowNum) ->
                new Reservation(resultSet.getLong(column_id),
                        resultSet.getLong(column_reviews_id),
                        resultSet.getLong(column_users_id),
                        resultSet.getLong(column_ticket_id),
                        resultSet.getBoolean(column_confirmed));

        List<Reservation> reservations = jdbcTemplate.query(sql, mapper);
        return reservations;
    }

    public List<ReservationsForUserResponse> getAllReservationForAUser(long userId)
    {
        String sql = "SELECT re.ID_Reservation, us.username, us.email, re.Confirmed, ti.Number_Seat, ti.Price, ti.Date_Bought\n" +
                " FROM Users us JOIN Reservations re ON us.ID_User = re.Users_ID\n" +
                " JOIN Tickets ti ON ti.ID_Ticket = re.Tickets_ID\n" +
                " WHERE us.ID_User = ?";

        RowMapper<ReservationsForUserResponse> mapper = (resultSet, rowNum) ->
                new ReservationsForUserResponse(resultSet.getLong("ID_Reservation"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("Confirmed"),
                        resultSet.getInt("Number_Seat"),
                        resultSet.getInt("Price"),
                        resultSet.getTimestamp("Date_Bought").toString()
                        );

        List<ReservationsForUserResponse> reservations = jdbcTemplate.query(sql, mapper, userId);
        return reservations;
    }

    public int confirmReservation(long idReservation)
    {
        String sql = "update Reservations\n" +
                " set Confirmed = 1\n" +
                " where ID_Reservation = ?";

        int numberOfUpdatedReservations = jdbcTemplate.update(sql, idReservation);
        return numberOfUpdatedReservations;
    }
}
