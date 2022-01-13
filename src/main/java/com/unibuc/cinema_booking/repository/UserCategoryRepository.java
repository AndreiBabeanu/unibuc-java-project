package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.exception.MethodNotImplementedException;
import com.unibuc.cinema_booking.model.EUserCategory;
import com.unibuc.cinema_booking.model.UserCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserCategoryRepository implements IDaoRepository<UserCategory> {

    final private String table_user_category = "User_Category";
    final private String column_id_user_category = "ID_User_Category";
    final private String column_category_name = "Category_Name";
    final private String column_reservation_min_time = "Reservation_Min_Time";
    final private String column_discount = "Discount";
    final private String column_minim_points = "Minim_points";

    private JdbcTemplate jdbcTemplate;

    public UserCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // no implementation is offered for this method, because the categories of user should be pre-established from the database,
    // without the possibility to create new ones, for the logic of this application.
    @Override
    public UserCategory create(UserCategory userCategory) {
        throw new MethodNotImplementedException("< create > for user category");
    }

    @Override
    public boolean delete(Long id) {
        throw new MethodNotImplementedException("< delete > for user category");
    }

    @Override
    public Optional<UserCategory> getOne(Long id) {
        String sql = String.format("select * from %s uc where uc.%s = ?",table_user_category,column_id_user_category);

        RowMapper<UserCategory> mapper = (resultSet, rowNum) ->
                new UserCategory(resultSet.getLong(column_id_user_category),
                        EUserCategory.valueOf(resultSet.getString(column_category_name)),
                        resultSet.getInt(column_reservation_min_time),
                        resultSet.getInt(column_discount),
                        resultSet.getInt(column_minim_points));


        List<UserCategory> userCategories = jdbcTemplate.query(sql, mapper, id);
        if(!userCategories.isEmpty()) {
            return Optional.of(userCategories.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<UserCategory> getAll() {
        String sql = String.format("select * from %s ",table_user_category);

        RowMapper<UserCategory> mapper = (resultSet, rowNum) ->
                new UserCategory(resultSet.getLong(column_id_user_category),
                        EUserCategory.valueOf(resultSet.getString(column_category_name)),
                        resultSet.getInt(column_reservation_min_time),
                        resultSet.getInt(column_discount),
                        resultSet.getInt(column_minim_points));

        List<UserCategory> userCategory = jdbcTemplate.query(sql, mapper);
        return userCategory;
    }

    public Optional<UserCategory> getUserCategoryByName(EUserCategory userCategory) {
        String sql = String.format("select * from %s uc where uc.%s = ? ",table_user_category,column_category_name);

        RowMapper<UserCategory> mapper = (resultSet, rowNum) ->
                new UserCategory(resultSet.getLong(column_id_user_category),
                        EUserCategory.valueOf(resultSet.getString(column_category_name)),
                        resultSet.getInt(column_reservation_min_time),
                        resultSet.getInt(column_discount),
                        resultSet.getInt(column_minim_points));


        List<UserCategory> userCategories = jdbcTemplate.query(sql, mapper, userCategory.toString());
        if(!userCategories.isEmpty()) {
            return Optional.of(userCategories.get(0));
        } else {
            return Optional.empty();
        }
    }

}
