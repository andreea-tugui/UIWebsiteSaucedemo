package com.webui.saucedemo.pages;


import com.codeborne.selenide.SelenideElement;
import com.webui.saucedemo.utils.PageConstants;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutPage {

    private final SelenideElement firstNameInput = $(PageConstants.FIRST_NAME);
    private final SelenideElement lastNameInput = $(PageConstants.LAST_NAME);
    private final SelenideElement postalCodeInput = $(PageConstants.POSTAL_CODE);
    private final SelenideElement continueButton = $(PageConstants.CONTINUE);
    private final SelenideElement finishButton = $(PageConstants.FINISH);

    @Step("Enter shipping information")
    public CheckoutPage enterShippingInformation(String firstName, String lastName, String postalCode) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        postalCodeInput.setValue(postalCode);
        continueButton.click();
        return this;
    }

    @Step("Complete the order")
    public void completeOrder() {
        finishButton.click();
        $("#checkout_complete_container").shouldBe(visible);
    }
}