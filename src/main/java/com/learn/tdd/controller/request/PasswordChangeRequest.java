package com.learn.tdd.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PasswordChangeRequest {
    private static final String USERNAME_ERROR = "用户名错误";
    private static final String PASSWORD_ERROR = "密码错误";

    @NotNull(message = USERNAME_ERROR)
    @Length(min = 6, max = 32, message = USERNAME_ERROR)
    private String username;

    @NotNull(message = PASSWORD_ERROR)
    @Length(min = 8, max = 24, message = PASSWORD_ERROR)
    private String password;

    @NotNull(message = PASSWORD_ERROR)
    @Length(min = 8, max = 24, message = PASSWORD_ERROR)
    private String newPassword;

    @NotNull(message = PASSWORD_ERROR)
    @Length(min = 8, max = 24, message = PASSWORD_ERROR)
    private String repeatPassword;


}
