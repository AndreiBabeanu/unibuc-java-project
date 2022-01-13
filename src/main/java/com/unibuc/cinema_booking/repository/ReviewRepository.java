package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.exception.MethodNotImplementedException;
import com.unibuc.cinema_booking.model.Location;
import com.unibuc.cinema_booking.model.Review;
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
public class ReviewRepository implements IDaoRepository<Review> {

    final private String table_reviews = "Reviews";
    final private String column_id = "ID_Review";
    final private String column_liked = "Liked";
    final private String column_stars = "Stars";
    final private String column_description = "Description";

    private JdbcTemplate jdbcTemplate;

    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Review create(Review review) {

        String sql = String.format("insert into %s values (?, ?, ?, ?)", table_reviews);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setBoolean(2, review.isLiked());
            preparedStatement.setInt(3, review.getStars());
            preparedStatement.setString(4, review.getDescription());
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        //set id-ul introdus pentru userul deja creat
        review.setId(generatedKeyHolder.getKey().longValue());
        return review;

    }

    @Override
    public boolean delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_reviews,column_id);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Optional<Review> getOne(Long id) {
        String sql = String.format("select * from %s l where l.%s = ?",table_reviews,column_id);

        RowMapper<Review> mapper = (resultSet, rowNum) ->
                new Review(resultSet.getLong(column_id),
                        resultSet.getBoolean(column_liked),
                        resultSet.getInt(column_stars),
                        resultSet.getString(column_description));


        List<Review> reviews = jdbcTemplate.query(sql, mapper, id);
        if(!reviews.isEmpty()) {
            return Optional.of(reviews.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Review> getAll() {
        String sql = String.format("select * from %s",table_reviews);

        RowMapper<Review> mapper = (resultSet, rowNum) ->
                new Review(resultSet.getLong(column_id),
                        resultSet.getBoolean(column_liked),
                        resultSet.getInt(column_stars),
                        resultSet.getString(column_description));

        List<Review> reviews = jdbcTemplate.query(sql, mapper);
        return reviews;
    }

}
