package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.model.Ticket;
import com.unibuc.cinema_booking.model.User;
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
public class TicketRepository implements IDaoRepository<Ticket> {

    final private String table_tickets = "Tickets";
    final private String column_id = "ID_Ticket";
    final private String column_movie_Showtime_id = "Movie_Showtimes_ID";
    final private String column_price = "Price";
    final private String column_number_seat = "Number_Seat";
    final private String column_data_bought = "Date_Bought";

    private JdbcTemplate jdbcTemplate;

    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ticket create(Ticket ticket) {
        String sql = String.format("insert into %s values (?, ?, ?, ?, ?)",table_tickets);
        ticket.setDate_bought(new Timestamp(System.currentTimeMillis()));
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, ticket.getMovieShowtimeId());  // default ID for bronze;
            preparedStatement.setInt(3, ticket.getPrice());
            preparedStatement.setInt(4, ticket.getNumberSeat());
            preparedStatement.setObject(5, ticket.getDate_bought());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        ticket.setId(generatedKeyHolder.getKey().longValue());
        return ticket;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_tickets,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Ticket> getOne(Long id) {
        String sql = String.format("select * from %s u where u.%s = ?",table_tickets,column_id);

        RowMapper<Ticket> mapper = (resultSet, rowNum) ->
                new Ticket(resultSet.getLong(column_id),
                        resultSet.getLong(column_movie_Showtime_id),
                        resultSet.getInt(column_price),
                        resultSet.getInt(column_number_seat),
                        (Timestamp) resultSet.getObject(column_data_bought));

        List<Ticket> tickets = jdbcTemplate.query(sql, mapper, id);
        if(!tickets.isEmpty()) {
            return Optional.of(tickets.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Ticket> getAll() {
        String sql = String.format("select * from %s",table_tickets);

        RowMapper<Ticket> mapper = (resultSet, rowNum) ->
                new Ticket(resultSet.getLong(column_id),
                        resultSet.getLong(column_movie_Showtime_id),
                        resultSet.getInt(column_price),
                        resultSet.getInt(column_number_seat),
                        (Timestamp) resultSet.getObject(column_data_bought));

        List<Ticket> tickets = jdbcTemplate.query(sql, mapper);
        return tickets;
    }
}
