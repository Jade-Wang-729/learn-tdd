package com.learn.tdd.service;

import com.learn.tdd.entity.Account;
import com.learn.tdd.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void register(String username, String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("用户名已使用");
        }
        accountRepository.save(new Account(username, password));
    }

    public boolean login(String username, String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            final String passwordRepository = accountRepository.findByUsername(username).get().getPassword();
            return passwordRepository.equals(password);
        }
        return false;
    }
}
