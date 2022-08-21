package com.learn.tdd.repository;
import com.learn.tdd.BaseRepositoryTest;
import com.learn.tdd.entity.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class NotificationRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_save_success_when_given_an_existing_id_and_valid_content() {
        // given
        Notification notification = new Notification("id", "valid content", "unread");
        // when
        notificationRepository.save(notification);
        // then
        dbAssertThat("select * from notifications where id='id'").hasNumberOfRows(1);
    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_success_when_given_an_existing_id_and_valid_content() {
        Optional<Notification> notification = notificationRepository.findByIdAndContent("id","valid content");

        assertThat(notification.isPresent()).isTrue();
        assertThat(notification.get().getContent()).isEqualTo("valid content");

        notification.get().setStatus("approve");
        notificationRepository.save(notification.get());

        Optional<Notification> notificationChanged = notificationRepository.findByIdAndContent("id","valid content");
        notificationChanged.ifPresent(value -> assertThat(value.getStatus()).isEqualTo("approve"));

    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_fail_when_given_not_existing_id_and_invalid_content() {
        Optional<Notification> notification = notificationRepository.findByIdAndContent("idWrong","valid content");

        assertThat(notification.isEmpty()).isTrue();

    }
    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_fail_when_given_an_existing_id_and_invalid_content() {
        Optional<Notification> notification = notificationRepository.findByIdAndContent("idWrong","invalid content");

        assertThat(notification.isEmpty()).isTrue();

    }
}
