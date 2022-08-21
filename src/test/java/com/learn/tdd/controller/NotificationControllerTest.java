package com.learn.tdd.controller;

import com.learn.tdd.BaseApiTest;
import com.learn.tdd.controller.request.AccountRequest;
import com.learn.tdd.controller.request.NotificationRequest;
import com.learn.tdd.controller.request.PasswordChangeRequest;
import com.learn.tdd.controller.request.PasswordValidateRequest;
import com.learn.tdd.service.PasswordValidateService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NotificationControllerTest extends BaseApiTest {

    private static final String NOTIFY_URL = "/notify";

    //register
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_notify_success_given_existing_id_and_valid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();

        notificationRequest.setId("id");
        notificationRequest.setContent("valid content");
        notificationRequest.setStatus("unread");

        // when
        given().body(notificationRequest).post(NOTIFY_URL).then().status(HttpStatus.OK);

        // then
        dbAssertThat("select * from notifications where id = ?", notificationRequest.getId()).hasNumberOfRows(1);
    }

}
