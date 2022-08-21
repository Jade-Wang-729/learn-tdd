package com.learn.tdd.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NotificationSearchRequest {
    private static final String USER_ID_ERROR = "User ID错误";

    @NotNull(message = USER_ID_ERROR)
    @Length(max = 64, message = USER_ID_ERROR)
    private String userId;

}
