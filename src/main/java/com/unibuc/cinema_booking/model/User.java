package com.unibuc.cinema_booking.model;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Timestamp accountCreated;
    private long userCategoryId;
    private long points;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username, String password, String name, String email, Timestamp accountCreated, long userCategoryId, long points) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountCreated = accountCreated;
        this.userCategoryId = userCategoryId;
        this.points = points;
    }

}
