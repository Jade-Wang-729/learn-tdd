package com.learn.tdd.controller;

import com.learn.tdd.controller.request.AccountRequest;
import com.learn.tdd.controller.request.PasswordChangeRequest;
import com.learn.tdd.controller.request.PasswordValidateRequest;
import com.learn.tdd.service.AccountService;
import com.learn.tdd.service.PasswordValidateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private PasswordValidateService passwordValidateService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid AccountRequest account) {
        accountService.register(account.getUsername(), account.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AccountRequest account) {
        String message =  accountService.login(account.getUsername(), account.getPassword());

        if (message.equals(accountService.SUCCESS)) {
            return ResponseEntity.ok(accountService.SUCCESS);
        }
        return ResponseEntity.badRequest().body(message);
    }

    /**
     * 密码规则：
     *  1. 长度在12-24之间
     *  2. 必需同时包含大写字母、小写字母、数字、特殊字符
     *  3. 特殊字符只能是括号中内容：[!@#$%&+=:;]
     *  4. 不能有两个以上的连续字母和数字，比如：123，abc是不被允许的
     *  5. 字符不能重复超过两次，比如：111，222，aaa是不被允许的
     */
    @PostMapping("/password/validate")
    public ResponseEntity<String> validateAccount(@RequestBody PasswordValidateRequest request) {
        String message = passwordValidateService.validate(Objects.isNull(request.getBase64Password())
                ? null : Base64.getDecoder().decode(request.getBase64Password()));
        if (message.equals(PasswordValidateService.SUCCESS)) {
            return ResponseEntity.ok(PasswordValidateService.SUCCESS);
        }
        return ResponseEntity.badRequest().body(message);
    }
}
