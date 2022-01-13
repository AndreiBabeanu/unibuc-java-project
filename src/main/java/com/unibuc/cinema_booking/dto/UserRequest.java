package com.unibuc.cinema_booking.dto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private long id;

    @NotBlank
    @Length(min = 4, max = 16)
    private String username;

    @NotBlank
    @Length(min = 4, max = 40)
    private String password;

    @NotBlank
    @Length(min = 4, max = 50)
    private String name;

    @Email
    @NotBlank
    private String email;

    private Timestamp accountCreated;

    @NotNull
    private long userCategoryId;

    @Min(0)
    private long points;

    public UserRequest(@NotNull @Length(min = 6, max = 16) String username, @NotNull @Length(min = 6, max = 40) String password, @NotNull @Length(min = 6, max = 50) String name, @Email @NotNull String email, @NotNull Timestamp accountCreated, @NotNull long userCategoryId, @Min(0) long points) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountCreated = accountCreated;
        this.userCategoryId = userCategoryId;
        this.points= points;
    }

}
