package com.learn.tdd.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class NotificationRequest {
    private static final String ID_ERROR = "ID错误";
    private static final String CONTENT_ERROR = "内容错误";
    private static final String STATUS_ERROR = "状态错误";

    @NotNull(message = ID_ERROR)
    @Length(max = 64, message = ID_ERROR)
    private String id;

    @NotNull(message = CONTENT_ERROR)
    @Length(max = 512, message = CONTENT_ERROR)
    private String content;

    @NotNull(message = STATUS_ERROR)
    @Length(max = 32, message = STATUS_ERROR)
    private String status;
}
