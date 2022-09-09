package com.learn.tdd.controller;

import com.learn.tdd.BaseApiTest;
import com.learn.tdd.controller.request.NotificationRequest;
import com.learn.tdd.controller.request.NotificationSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static java.util.Collections.emptyList;
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
//        ["valid content 1", "valid content 2", "valid content 3"];
        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.OK)
                .body("contentList",equalTo(List.of("valid content 1", "valid content 2", "valid content 3")));

    }
    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_one_given_existing_id() {
        // given
        NotificationSearchRequest notificationSearchRequest = new NotificationSearchRequest();
        notificationSearchRequest.setUserId("id");

        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.OK)
                .body("contentList",equalTo(List.of("valid content")));

    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_null_given_existing_id() {
        // given
        NotificationSearchRequest notificationSearchRequest = new NotificationSearchRequest();
        notificationSearchRequest.setUserId("id");
        // when
        given().body(notificationSearchRequest).post(SEARCH_NOTIFY_URL).then().status(HttpStatus.OK)
                .body("contentList",equalTo(emptyList()));

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
