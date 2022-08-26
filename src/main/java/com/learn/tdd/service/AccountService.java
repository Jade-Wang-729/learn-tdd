package com.learn.tdd.service;

import com.learn.tdd.entity.Account;
import com.learn.tdd.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    public final String SUCCESS = "SUCCESS";

    public void register(String username, String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("用户名已使用");
        }
        accountRepository.save(new Account(username, password));
    }

    public String login(String username, String password) {

        if (accountRepository.findByUsername(username).isEmpty()) {
            throw new RuntimeException("用户名或密码错误");

        }
        final String passwordRepository = accountRepository.findByUsername(username).get().getPassword();

        if (!passwordRepository.equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        return SUCCESS;
    }

    public String changePassword(String username, String password, String newPassword, String repeatPassword) {
        final String loginResult = login(username, password);
        if (!loginResult.equals(SUCCESS)) {
            return loginResult;
        }
        if (!Objects.equals(newPassword, repeatPassword)) {
            throw new RuntimeException("密码不一致");
        }
        if (accountRepository.findByUsername(username).isEmpty()) {
            throw new RuntimeException("用户名或密码错误");
        }
        Account userChange = accountRepository.findByUsername(username).get();
        userChange.setPassword(newPassword);

        accountRepository.save(userChange);

        return SUCCESS;

    }
}
