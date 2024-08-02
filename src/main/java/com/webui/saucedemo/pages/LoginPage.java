package com.webui.saucedemo.pages;


import com.codeborne.selenide.SelenideElement;
import com.webui.saucedemo.utils.PageConstants;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessageContainer = $(".error-message-container");

    @Step("Open login page")
    public LoginPage openPage() {
        open(PageConstants.HTTPS_WWW_SAUCEDEMO_COM);
        return this;
    }

    @Step("Login as user: {username}")
    public InventoryPage login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        return new InventoryPage();
    }

    @Step("Login should fail")
    public void loginShouldFail(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        $(PageConstants.ERROR_MESSAGE_CONTAINER).shouldBe(visible);
    }

    @Step("Get error message text")
    public String getErrorMessageText() {
        return errorMessageContainer.getText();
    }
}