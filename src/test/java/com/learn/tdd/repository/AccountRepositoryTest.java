package com.learn.tdd.repository;


import com.learn.tdd.BaseRepositoryTest;
import com.learn.tdd.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void should_save_success_when_save_given_an_username_and_password() {
        // given
        Account account = new Account("TestUser", "password");

        // when
        accountRepository.save(account);

        // then
        dbAssertThat("select * from accounts where username='TestUser'").hasNumberOfRows(1);
    }

    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_account_when_findByUsername_given_and_exist_username() {
        // when
        Optional<Account> account = accountRepository.findByUsername("TestUser");

        // then
        assertThat(account.isPresent()).isTrue();
    }


    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_account_and_username_when_findByUsername_given_and_exist_username() {
        // when
        Optional<Account> account = accountRepository.findByUsername("TestUser");

        // then
        assertThat(account.isPresent()).isTrue();
        assertThat(account.get().getPassword()).isEqualTo("password");
    }

    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_update_account_when_change_password_given_exist_username_and_its_password_and_password() {
        // when
        Optional<Account> account = accountRepository.findByUsername("TestUser");
        assertThat(account.isPresent()).isTrue();
        assertThat(account.get().getPassword()).isEqualTo("password");

        Optional<Account> userChange = accountRepository.findByUsername("TestUser");
        if (userChange.isPresent()) {
        userChange.get().setPassword("newPassword");
        accountRepository.save(userChange.get());
        }
        Optional<Account> accountChange = accountRepository.findByUsername("TestUser");
        accountChange.ifPresent(value -> assertThat(value.getPassword()).isEqualTo("newPassword"));
    }
}
