package com.unibuc.cinema_booking.repository;

import com.unibuc.cinema_booking.dto.HistoryReservationsUserResponse;
import com.unibuc.cinema_booking.dto.UpdateUserCategoryRequest;
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
public class UserRepository implements IDaoRepository<User> {

    /* I wanted to make the code more secure and easy to change the names from database and to not hardcoded the columns name of the tables,
   but i thinks it's more ugly and for the others entity i'm not sure if i had to do that again */

    final private String table_users = "USERS";
    final private String column_id_user = "ID_User";
    final private String column_id_user_category = "User_Category_ID";
    final private String column_username = "Username";
    final private String column_password = "Password";
    final private String column_name = "Name";
    final private String column_date_created = "Date_Created";
    final private String column_email = "Email";
    final private String column_points = "Points";


    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        String sql = String.format("insert into %s values (?, ?, ?, ?, ?, ?, ?, ?)",table_users);
        user.setAccountCreated(new Timestamp(System.currentTimeMillis()));
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setObject(2, user.getUserCategoryId());  // default ID for bronze;
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setObject(6, user.getAccountCreated());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setLong(8,0); //
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        //set id-ul introdus pentru userul deja creat
        user.setId(generatedKeyHolder.getKey().longValue());
        return user;

        // daca vrem sa fim super corecti, trebuie sa facem un get cu id-ul respectiv.
        // Optional<User> userSaved = getOne(generatedKeyHolder.getKey().longValue());
        // return ((userSaved.isEmpty()) ? Optional.empty() : userSaved);
    }


    @Override
    public boolean delete(Long id) {

        String sql = String.format("DELETE FROM %s WHERE %s = ?",table_users,column_id_user);
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }


    @Override
    public Optional<User> getOne(Long id) {
        String sql = String.format("select * from %s u where u.%s = ?",table_users,column_id_user);

        RowMapper<User> mapper = (resultSet, rowNum) ->
                new User(resultSet.getLong(column_id_user),
                        resultSet.getString(column_username),
                        resultSet.getString(column_password),
                        resultSet.getString(column_email),
                        resultSet.getString(column_name),
                        (Timestamp) resultSet.getObject(column_date_created),
                        resultSet.getLong(column_id_user_category),
                        resultSet.getLong(column_points));


        List<User> users = jdbcTemplate.query(sql, mapper, id);
        if(!users.isEmpty()) {
            return Optional.of(users.get(0));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<User> getAll() {
        String sql = String.format("select * from %s",table_users);

        RowMapper<User> mapper = (resultSet, rowNum) ->
                new User(resultSet.getLong(column_id_user),
                        resultSet.getString(column_username),
                        resultSet.getString(column_password),
                        resultSet.getString(column_email),
                        resultSet.getString(column_name),
                        (Timestamp) resultSet.getObject(column_date_created),
                        resultSet.getLong(column_id_user_category),
                        resultSet.getLong(column_points));

        List<User> users = jdbcTemplate.query(sql, mapper);
        return users;

    }

    //return the number of updated entities
    public int updateUserCategory(UpdateUserCategoryRequest updateUser) {

        String sql = String.format("update %s u set u.%s = ? where u.%s = ?",table_users,column_id_user_category,column_id_user);
        int numberOfUpdatedUsers = jdbcTemplate.update(sql, updateUser.getUserCategoryId(), updateUser.getUserId());
        return numberOfUpdatedUsers;
    }

    public Boolean checkUsernameExist(String username) {
        String sql = String.format("select * from %s u where u.%s = ?",table_users,column_username);

        RowMapper<User> mapper = (resultSet, rowNum) ->
                new User(resultSet.getLong(column_id_user),
                        resultSet.getString(column_username));

        List<User> users = jdbcTemplate.query(sql, mapper, username);
        return users.size() > 0;
    }

    public Optional<User> getUserForLogin(String username, String password) {
        String sql = String.format("select * from %s u where u.%s = ? AND u.%s = ?",table_users,column_username,column_password);

        RowMapper<User> mapper = (resultSet, rowNum) ->
                new User(resultSet.getLong(column_id_user),
                        resultSet.getString(column_username),
                        resultSet.getString(column_password),
                        resultSet.getString(column_email),
                        resultSet.getString(column_name),
                        (Timestamp) resultSet.getObject(column_date_created),
                        resultSet.getLong(column_id_user_category),
                        resultSet.getLong(column_points));


        List<User> users = jdbcTemplate.query(sql, mapper, username, password);
        if(!users.isEmpty()) {
            return Optional.of(users.get(0));
        } else {
            return Optional.empty();
        }
    }

    public List<HistoryReservationsUserResponse> getUserHistoryOfReservations(long userId)
    {
        String sql = "SELECT us.username, us.email, re.Confirmed, ti.Number_Seat, ti.Price, ti.Date_Bought\n" +
                " FROM Users us JOIN Reservations re ON us.ID_User = re.Users_ID\n" +
                " JOIN Tickets ti ON ti.ID_Ticket = re.Tickets_ID\n" +
                " WHERE us.ID_User = ?;";

        RowMapper<HistoryReservationsUserResponse> mapper = (resultSet, rowNum) ->
                new HistoryReservationsUserResponse(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("Confirmed"),
                        resultSet.getInt("Number_Seat"),
                        resultSet.getInt("Price"),
                        ((Timestamp) resultSet.getObject("Date_Bought")).toString());

        List<HistoryReservationsUserResponse> history = jdbcTemplate.query(sql, mapper,userId);
        return history;

    }

    public int getUserDiscount(long userId)
    {
        String sql = "select Discount from Users u JOIN User_Category uc ON u.User_Category_ID = uc.ID_User_Category WHERE u.ID_User = ?";
        RowMapper<Integer> mapper = (resultSet, rowNum) ->
                resultSet.getInt("Discount");

        List<Integer> discount = jdbcTemplate.query(sql,mapper,userId);
        return discount.get(0);

    }

    public int updatePointsUser(int points, long idUser)
    {
        String sql = "update Users\n" +
                "set points = ?\n" +
                "where ID_User = ? ";

        int numberOfUpdatedUsers = jdbcTemplate.update(sql, points, idUser);
        return numberOfUpdatedUsers;
    }

}
