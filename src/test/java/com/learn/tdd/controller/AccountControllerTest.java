package com.learn.tdd.controller;

import com.learn.tdd.BaseApiTest;
import com.learn.tdd.controller.request.AccountRequest;
import com.learn.tdd.controller.request.PasswordChangeRequest;
import com.learn.tdd.controller.request.PasswordValidateRequest;
import com.learn.tdd.service.PasswordValidateService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountControllerTest extends BaseApiTest {
    private static final String REGISTER_URL = "/accounts/register";
    private static final String PASSWORD_VALIDATE_URL = "/accounts/password/validate";

    //register
    @Test
    void should_register_success_when_register_given_valid_username_and_valid_password() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUser");
        request.setPassword("password001");

        // when
        given().body(request).post("/accounts/register").then().status(HttpStatus.OK);

        // then
        dbAssertThat("select * from accounts where username = ?", request.getUsername()).hasNumberOfRows(1);
    }


    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_return_400_when_register_given_exist_username_and_valid_password() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUser");
        request.setPassword("password001");

        // when
        given().body(request).post(REGISTER_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("用户名已使用"));
    }

    @Test
    void should_return_username_error_when_register_given_username_len_less_than_6() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("user");
        request.setPassword("password001");

        // when then
        given().body(request).post(REGISTER_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("用户名错误"));
    }

    @Test
    void should_return_username_error_when_register_given_username_len_greater_than_32() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("useruseruseruseruseruseruseruseruser");
        request.setPassword("password001");

        // when then
        given().body(request).post(REGISTER_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("用户名错误"));
    }

    @Test
    void should_return_password_error_when_register_given_password_len_less_than_8() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUser");
        request.setPassword("pwd");

        // when then
        given().body(request).post(REGISTER_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("密码错误"));
    }

    @Test
    void should_return_password_error_when_register_given_password_len_greater_than_32() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUser");
        request.setPassword("passwordpasswordpassword1");

        // when then
        given().body(request).post(REGISTER_URL).then().status(HttpStatus.BAD_REQUEST)
                .body(equalTo("密码错误"));
    }

    @Test
    void should_return_password_error_when_register_given_invalid_username_and_invalid_password() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("user");
        request.setPassword("pwd");

        // when
        String error = given().body(request).post(REGISTER_URL).then().status(HttpStatus.BAD_REQUEST)
                .extract().asString();

        // then
        assertThat(error).contains("用户名错误", "密码错误");
    }

    @Test
    void should_return_success_when_validate_given_valid_password() {
        String password = Base64.getEncoder().encodeToString("Aqw13579$@12a".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.OK).extract().asString();

        assertThat(result).isEqualTo(PasswordValidateService.SUCCESS);
    }

    @Test
    void should_return_len_error_when_validate_given_null_password() {
        String result = given().body(new PasswordValidateRequest()).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码长度必需在12~24");
    }

    @Test
    void should_return_len_error_when_validate_given_password_len_less_than_12() {
        String password = Base64.getEncoder().encodeToString("123".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码长度必需在12~24");
    }

    @Test
    void should_return_len_error_when_validate_given_password_len_greater_than_24() {
        String password = Base64.getEncoder().encodeToString("123456789012345678901234567890".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码长度必需在12~24");
    }

    @Test
    void should_return_pattern_error_when_validate_given_password_not_contains_uppercase_letter() {
        String password = Base64.getEncoder().encodeToString("ab12qwedcvgsdfghj".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码必需包含大写字母、小写字母、数字和特殊字符");
    }

    @Test
    void should_return_pattern_error_when_validate_given_password_not_contains_lowercase_letter() {
        String password = Base64.getEncoder().encodeToString("AB12QWEDCVGSDFGHJ".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码必需包含大写字母、小写字母、数字和特殊字符");
    }

    @Test
    void should_return_pattern_error_when_validate_given_password_not_contains_number_letter() {
        String password = Base64.getEncoder().encodeToString("ABasvxdQWEDCVGSDFGHJ".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码必需包含大写字母、小写字母、数字和特殊字符");
    }

    @Test
    void should_return_pattern_error_when_validate_given_password_not_contains_special_letter() {
        String password = Base64.getEncoder().encodeToString("ABasvxdQWEDCVGSDFG@$".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("密码必需包含大写字母、小写字母、数字和特殊字符");
    }

    @Test
    void should_return_unexpected_letter_error_when_validate_given_password_not_contains_unexpected_letter() {
        String password = Base64.getEncoder().encodeToString("ABasv1xdQWECVGSDFG@$?".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("特殊字符只能包含：!@#$%&+=:;");
    }

    @Test
    void should_return_continuous_letter_error_when_validate_given_password_not_contains_continuous_letter() {
        String password = Base64.getEncoder().encodeToString("ABCsv1xdQWEDCVGSDFG@$".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("连续字符不能超过两个");
    }

    @Test
    void should_return_repeat_letter_error_when_validate_given_password_not_contains_repeat_letter() {
        String password = Base64.getEncoder().encodeToString("AAAsvx1dQWEaCVGSDFG@$".getBytes(StandardCharsets.UTF_8));

        String result = given().body(new PasswordValidateRequest(password)).post(PASSWORD_VALIDATE_URL)
                .then().status(HttpStatus.BAD_REQUEST).extract().asString();

        assertThat(result).isEqualTo("连续相同字符不能超过两个");
    }

    //login
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_login_success_when_login_given_valid_username_and_valid_password() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUser");
        request.setPassword("password");

        given().body(request).post("/accounts/login").then().status(HttpStatus.OK);
    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_login_fail_when_login_given_valid_username_and_wrong_password() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUser");
        request.setPassword("password0012dfbfdbdf");

        given().body(request).post("/accounts/login").then().status(HttpStatus.BAD_REQUEST).body(equalTo("用户名或密码错误"));
        // when
        // then
    }

    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_login_fail_when_login_given_invalid_username_and_password() {
        // given
        AccountRequest request = new AccountRequest();
        request.setUsername("TestUserWrong");
        request.setPassword("password");

        given().body(request).post("/accounts/login").then().status(HttpStatus.BAD_REQUEST).body(equalTo("用户名或密码错误"));
        // when
        // then
    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_update_success_when_change_password_given_valid_username_and_password_and_same_password() {
        // given
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setUsername("TestUser");
        request.setPassword("password");
        request.setNewPassword("newPassword");
        request.setRepeatPassword("newPassword");

        given().body(request).post("/accounts/changePassword").then().status(HttpStatus.OK);
    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_update_fail_when_change_password_given_wrong_username_or_password_and_same_password() {
        // given
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setUsername("TestUserWrong");
        request.setPassword("password");
        request.setNewPassword("newPassword");
        request.setRepeatPassword("newPassword");

        given().body(request).post("/accounts/changePassword").then().status(HttpStatus.BAD_REQUEST).body(equalTo("用户名或密码错误"));
    }
    @Test
    @Sql("classpath:sql/insertUserToDb.sql")
    void should_update_fail_when_change_password_given_valid_username_and_password_and_different_password() {
        // given
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setUsername("TestUser");
        request.setPassword("password");
        request.setNewPassword("newPassword");
        request.setRepeatPassword("differentPassword");

        given().body(request).post("/accounts/changePassword").then().status(HttpStatus.BAD_REQUEST).body(equalTo("密码不一致"));
    }
}
