package com.learn.tdd.service;

import com.learn.tdd.BaseUnitTest;
import com.learn.tdd.entity.Account;
import com.learn.tdd.entity.Notification;
import com.learn.tdd.repository.AccountRepository;
import com.learn.tdd.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class NotificationServiceTest extends BaseUnitTest {
    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "password001";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String SAME_REPEAT_PASSWORD = "newPassword";
    public static final String DIFFERENT_REPEAT_PASSWORD = "repeatPassword";
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;


    @Test
    void should_save_notification_success_when_give_notification_given_exist_id_and_valid_content() {
        // given
        final Account Account = new Account(USERNAME, PASSWORD);

        given(accountRepository.findById(anyString())).willReturn(Optional.of(Account));

        Notification notification = new Notification("id", "valid content", "unread");
        given(notificationRepository.save(any())).willReturn(notification);

        // when
        assertThat(notificationService.notify("id","valid content","unread")).isEqualTo(notificationService.SUCCESS);

    }

    @Test
    void should_save_notification_fail_when_give_notification_given_not_exist_id_and_valid_content() {
        // given

        given(accountRepository.findById("idWrong")).willReturn(Optional.empty());

        // when
        assertThat(notificationService.notify("idWrong","valid content","unread")).isEqualTo("用户不存在");

    }

    @Test
    void should_save_notification_fail_when_give_notification_given_exist_id_and_invalid_content() {
        // given
        final Account Account = new Account(USERNAME, PASSWORD);
        given(accountRepository.findById(anyString())).willReturn(Optional.of(Account));

        // when
        assertThat(notificationService.notify("id"," ","unread")).isEqualTo("内容为空");

    }
}
