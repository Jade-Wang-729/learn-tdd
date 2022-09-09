package com.learn.tdd.service;

import com.learn.tdd.BaseUnitTest;
import com.learn.tdd.entity.Account;
import com.learn.tdd.entity.Notification;
import com.learn.tdd.repository.AccountRepository;
import com.learn.tdd.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class NotificationServiceTest extends BaseUnitTest {
    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "password001";
    public static final String ID_WRONG = "idWrong";
    public static final String ID = "id";
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

        Notification notification = new Notification(ID, "valid content", "unread");
        given(notificationRepository.save(any())).willReturn(notification);

        // when
        assertThat(notificationService.notify(ID, "valid content", "unread")).isEqualTo(notificationService.SUCCESS);

    }

    @Test
    void should_save_notification_fail_when_give_notification_given_not_exist_id_and_valid_content() {
        // given

        given(accountRepository.findById("idWrong")).willReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class, () ->
                notificationService.notify("idWrong", "valid content", "unread"), "用户不存在");
    }

    @Test
    void should_save_notification_fail_when_give_notification_given_exist_id_and_invalid_content() {
        // given
        final Account Account = new Account(USERNAME, PASSWORD);
        given(accountRepository.findById(anyString())).willReturn(Optional.of(Account));

        // when
        assertThrows(RuntimeException.class, () ->
                notificationService.notify(ID, " ", "unread"), "内容为空");

    }

    @Test
    void should_update_notification_success_when_update_given_exist_id_and_valid_content() {
        // given
        Notification notification = new Notification(ID, "valid content", "unread");
        given(notificationRepository.findByUserIdAndContent(ID, "valid content")).willReturn(Optional.of(notification));

        Notification notificationToChange = new Notification(ID, "valid content", "approve");
        given(notificationRepository.save(any())).willReturn(notificationToChange);

        // when
        assertThat(notificationService.updateNotify(ID, "valid content", "approve")).isEqualTo(notificationService.SUCCESS);
    }

    @Test
    void should_update_notification_fail_when_update_given_not_exist_id_and_valid_content() {
        // given
        given(notificationRepository.findByUserIdAndContent("idWrong", "valid content")).willReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class, () ->
                notificationService.updateNotify("idWrong", "valid content", "approve"), "该条通知不存在");

    }

    @Test
    void should_update_notification_fail_when_update_given_exist_id_and_invalid_content() {
        // given
        given(notificationRepository.findByUserIdAndContent(ID, "invalid content")).willReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class, () ->
                notificationService.updateNotify(ID, "invalid content", "approve"), "该条通知不存在");

    }

    @Test
    void should_return_notification_all_when_search_given_exist_id() {
        // given
        Account account = new Account();
        given(accountRepository.findById(ID)).willReturn(Optional.of(account));

        List<Notification> notification = List.of(
                new Notification(ID, "valid content 1", "unread"),
                new Notification(ID, "valid content 2", "unread"),
                new Notification(ID, "valid content 3", "unread"));

        given(notificationRepository.findAllByUserId(ID)).willReturn(notification);

        // when
        assertThat(notificationService.searchNotify(ID))
                .isEqualTo(List.of("valid content 1", "valid content 2", "valid content 3"));

    }

    @Test
    void should_return_notification_one_when_search_given_exist_id() {
        // given

        Account account = new Account();
        given(accountRepository.findById(ID)).willReturn(Optional.of(account));

        List<Notification> notification = List.of(new Notification(ID, "valid content 1", "unread"));
        given(notificationRepository.findAllByUserId(ID)).willReturn(notification);

        // when
        assertThat(notificationService.searchNotify(ID))
                .isEqualTo(List.of("valid content 1"));

    }

    @Test
    void should_return_notification_null_when_search_given_exist_id() {
        // given
        Account account = new Account();
        given(accountRepository.findById(ID)).willReturn(Optional.of(account));

        List<Notification> notification = List.of();

        given(notificationRepository.findAllByUserId(ID)).willReturn(notification);

        // when
        assertThat(notificationService.searchNotify(ID))
                .isEqualTo(List.of());
    }

    @Test
    void should_return_message_null_when_search_given_not_exist_id() {
        // given
        given(accountRepository.findById(ID_WRONG)).willReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class, () ->
                notificationService.searchNotify(ID_WRONG), "用户不存在");

    }

}
