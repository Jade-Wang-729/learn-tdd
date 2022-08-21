package com.learn.tdd.repository;
import com.learn.tdd.BaseRepositoryTest;
import com.learn.tdd.entity.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;


class NotificationRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_save_success_when_given_an_existing_username_and_valid_content() {
        // given
        Notification notification = new Notification("id", "valid content", "unread");
        // when
        notificationRepository.save(notification);
        // then
        dbAssertThat("select * from notifications where id='id'").hasNumberOfRows(1);
    }
}
