package com.learn.tdd.repository;
import com.learn.tdd.BaseRepositoryTest;
import com.learn.tdd.entity.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class NotificationRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void should_save_success_when_given_an_existing_id_and_valid_content() {
        // given
        Notification notification = new Notification("id", "valid content", "unread");
        // when
        notificationRepository.save(notification);
        // then
        dbAssertThat("select * from notifications where user_id='id'").hasNumberOfRows(1);
    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_success_when_given_an_existing_id_and_valid_content() {
        Optional<Notification> notification = notificationRepository.findByUserIdAndContent("id","valid content");

        assertThat(notification.isPresent()).isTrue();

        notification.get().setStatus("approve");
        notificationRepository.save(notification.get());

        Optional<Notification> notificationChanged = notificationRepository.findByUserIdAndContent("id","valid content");
        notificationChanged.ifPresent(value -> assertThat(value.getStatus()).isEqualTo("approve"));

    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_fail_when_given_not_existing_id_and_invalid_content() {
        Optional<Notification> notification = notificationRepository.findByUserIdAndContent("idWrong","valid content");

        assertThat(notification.isEmpty()).isTrue();

    }
    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_update_fail_when_given_an_existing_id_and_invalid_content() {
        Optional<Notification> notification = notificationRepository.findByUserIdAndContent("idWrong","invalid content");

        assertThat(notification.isEmpty()).isTrue();

    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb2.sql")
    void should_return_all_when_given_an_existing_user_id() {
        List<Notification> notification = notificationRepository.findAllByUserId("id");

        assertThat(notification.size()).isEqualTo(3);

        assertThat(notification.get(0).getContent()).isEqualTo("valid content 1");
        assertThat(notification.get(1).getContent()).isEqualTo("valid content 2");
        assertThat(notification.get(2).getContent()).isEqualTo("valid content 3");

    }

    @Test
    @Sql("classpath:sql/insertNotificationToDb.sql")
    void should_return_one_when_given_an_existing_user_id() {
        List<Notification> notification = notificationRepository.findAllByUserId("id");

        assertThat(notification.size()).isEqualTo(1);

        assertThat(notification.get(0).getContent()).isEqualTo("valid content");

    }

    @Test
    void should_return_null_when_given_an_existing_user_id() {
        List<Notification> notification = notificationRepository.findAllByUserId("id");

        assertThat(notification.size()).isEqualTo(0);

    }


}
