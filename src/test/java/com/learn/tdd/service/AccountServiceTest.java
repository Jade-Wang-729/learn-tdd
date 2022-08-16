package com.learn.tdd.service;

import com.learn.tdd.BaseUnitTest;
import com.learn.tdd.entity.Account;
import com.learn.tdd.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class AccountServiceTest extends BaseUnitTest {
    private static final String USERNAME = "TestUser";
    private static final String PASSWORD = "password001";
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @Test
    void should_register_success_when_register_given_username_and_password_valid() {
        // given when then
        accountService.register(USERNAME, PASSWORD);
    }

    @Test
    void should_throw_exception_when_register_given_an_exist_username() {
        // given
        given(accountRepository.findByUsername(anyString())).willReturn(Optional.of(new Account()));

        // when
        assertThrows(RuntimeException.class, () -> accountService.register(USERNAME, PASSWORD), "用户名已使用");
    }

    @Test
    void should_login_success_when_login_given_an_exist_username_and_right_password() {
        // given
        given(accountRepository.findByUsername(anyString())).willReturn(Optional.of(new Account(USERNAME, "123")));
//        given(accountRepository.findByUsername(anyString())).willReturn(Optional.of(new Account(USERNAME,PASSWORD)));

        // when
        assertThat(accountService.login(USERNAME, "123")).isEqualTo(accountService.SUCCESS);
    }
}
