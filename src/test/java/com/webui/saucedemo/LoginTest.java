package com.webui.saucedemo;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class LoginTest {

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.baseUrl = "https://www.saucedemo.com";
        Configuration.browser = "chrome";
    }

    @Test
    @Description("Test for successful login with standard user and password")
    @Severity(SeverityLevel.CRITICAL)
    public void loginSuccessful() {
        open("/");
        login("standard_user", "secret_sauce");
        $(".inventory_list").shouldBe(Condition.visible);
    }

    @Test
    @Description("Test for unsuccessful login with locked out user")
    @Severity(SeverityLevel.CRITICAL)
    public void loginUnsuccessful() {
        open("/");
        login("locked_out_user", "secret_sauce");
        $(".error-message-container").shouldHave(Condition.text("Epic sadface: Sorry, this user has been locked out."));
    }

    @Step("Login with username {username} and password {password}")
    public static void login(String username, String password) {
        $("#user-name").setValue(username);
        $("#password").setValue(password);
        $("#login-button").click();
    }
}

