package com.unibuc.cinema_booking.repository;

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
public class LocationRepository implements  IDaoRepository<Location> {

    final private String table_locations = "Locations";
    final private String column_id_location = "ID_Location";
    final private String column_city = "City";
    final private String column_street = "Street";
    final private String column_postal_code = "Postal_Code";
    final private String column_nr = "Nr";


    private JdbcTemplate jdbcTemplate;

    public LocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location create(Location location) {

        String sql = String.format("insert into %s values (?, ?, ?, ?, ?)", table_locations);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, location.getCity());
            preparedStatement.setString(3, location.getStreet());
            preparedStatement.setString(4, location.getNumber());
            preparedStatement.setString(5, location.getPostalCode());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        //set id-ul introdus pentru userul deja creat
        location.setId(generatedKeyHolder.getKey().longValue());
        return location;
    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_locations,column_id_location);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Location> getOne(Long id) {
        String sql = String.format("select * from %s l where l.%s = ?",table_locations,column_id_location);

        RowMapper<Location> mapper = (resultSet, rowNum) ->
                new Location(resultSet.getLong(column_id_location),
                        resultSet.getString(column_city),
                        resultSet.getString(column_street),
                        resultSet.getString(column_nr),
                        resultSet.getString(column_postal_code));


        List<Location> locations = jdbcTemplate.query(sql, mapper, id);
        if(!locations.isEmpty()) {
            return Optional.of(locations.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Location> getAll() {
        String sql = String.format("select * from %s",table_locations);

        RowMapper<Location> mapper = (resultSet, rowNum) ->
                new Location(resultSet.getLong(column_id_location),
                        resultSet.getString(column_city),
                        resultSet.getString(column_street),
                        resultSet.getString(column_postal_code),
                        resultSet.getString(column_nr));

        List<Location> locations = jdbcTemplate.query(sql, mapper);
        return locations;
    }
}
