package com.learn.tdd.controller;

import com.learn.tdd.BaseApiTest;
import com.learn.tdd.controller.request.AccountRequest;
import com.learn.tdd.controller.request.NotificationRequest;
import com.learn.tdd.controller.request.NotificationSearchRequest;
import com.learn.tdd.controller.request.PasswordChangeRequest;
import com.learn.tdd.controller.request.PasswordValidateRequest;
import com.learn.tdd.service.PasswordValidateService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NotificationControllerTest extends BaseApiTest {

    private static final String NOTIFY_URL = "/notify";
    private static final String UPDATE_NOTIFY_URL = "/notify/update";
    private static final String SEARCH_NOTIFY_URL = "/notify/search";

    //register
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_notify_success_given_existing_id_and_valid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();

        notificationRequest.setId("notificationId");
        notificationRequest.setUserId("id");
        notificationRequest.setContent("valid content");
        notificationRequest.setStatus("unread");

        // when
        given().body(notificationRequest).post(NOTIFY_URL).then().status(HttpStatus.OK);

        // then
        dbAssertThat("select * from notifications where user_id = ?", notificationRequest.getUserId()).hasNumberOfRows(1);
    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_notify_fail_given_not_existing_id_and_valid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setId("notificationId");
        notificationRequest.setUserId("idWrong");
        notificationRequest.setContent("valid content");
        notificationRequest.setStatus("unread");

        // when
        given().body(notificationRequest).post(NOTIFY_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("用户不存在"));

    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_notify_fail_given_existing_id_and_invalid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setId("notificationId");
        notificationRequest.setUserId("id");
        notificationRequest.setContent(" ");
        notificationRequest.setStatus("unread");

        // when
        given().body(notificationRequest).post(NOTIFY_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("内容为空"));

    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_success_given_existing_id_and_valid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setId("notificationId");
        notificationRequest.setUserId("id");
        notificationRequest.setContent("valid content");
        notificationRequest.setStatus("approve");

        // when
        given().body(notificationRequest).post(UPDATE_NOTIFY_URL).then().status(HttpStatus.OK);

    }
    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_fail_given_not_existing_id_and_valid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setId("notificationId");
        notificationRequest.setUserId("idWrong");
        notificationRequest.setContent("valid content");
        notificationRequest.setStatus("approve");

        // when
        given().body(notificationRequest).post(UPDATE_NOTIFY_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("该条通知不存在"));

    }
    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_fail_given_existing_id_and_invalid_content() {
        // given
        NotificationRequest notificationRequest = new NotificationRequest();

        notificationRequest.setId("notificationId");
        notificationRequest.setUserId("id");
        notificationRequest.setContent("invalid content");
        notificationRequest.setStatus("approve");

        // when
        given().body(notificationRequest).post(UPDATE_NOTIFY_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("该条通知不存在"));

    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb2.sql")
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_all_given_existing_id() {
        // given
        NotificationSearchRequest notificationSearchRequest = new NotificationSearchRequest();
        notificationSearchRequest.setUserId("id");
        final String result = List.of("valid content 1", "valid content 2", "valid content 3").toString();
        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.OK)
                .body(equalTo(result));

    }
    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_one_given_existing_id() {
        // given
        NotificationSearchRequest notificationSearchRequest = new NotificationSearchRequest();
        notificationSearchRequest.setUserId("id");
        final String result = List.of("valid content").toString();
        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.OK)
                .body(equalTo(result));

    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_null_given_existing_id() {
        // given
        NotificationSearchRequest notificationSearchRequest = new NotificationSearchRequest();
        notificationSearchRequest.setUserId("id");
        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.OK)
                .body(equalTo("没有消息"));

    }
    @Test
    void should_return_error_given_not_existing_id() {
        // given
        NotificationSearchRequest notificationSearchRequest = new NotificationSearchRequest();
        notificationSearchRequest.setUserId("id");
        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("用户不存在"));

    }
}
