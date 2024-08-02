package com.webui.saucedemo;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;

import com.webui.saucedemo.pages.InventoryPage;
import com.webui.saucedemo.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.webui.saucedemo.utils.PageConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.Map;

@ExtendWith(AllureJunit5.class)
public class SaucedemoTests {


    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--password-manager-disabled");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "autofill.profile_enabled", false
        ));

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
    }


    @Test
    @Description("Test for successful login with standard user and password")
    @Severity(SeverityLevel.CRITICAL)
    public void loginSuccessfulTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .login(STANDARD_USER, SECRET_SAUCE);

        $("#inventory_container").shouldBe(visible);
    }

    @Test
    @Description("Test for unsuccessful login with locked out user")
    @Severity(SeverityLevel.CRITICAL)
    public void loginUnsuccessfulTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .loginShouldFail(LOCKED_OUT_USER, SECRET_SAUCE);

        String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
        Assertions.assertEquals(expectedErrorMessage, loginPage.getErrorMessageText());
    }

    @Test
    @Description("Sort Product list")
    public void sortProductListTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = loginPage.openPage()
                .login(STANDARD_USER, SECRET_SAUCE);


        inventoryPage.sortBy("Price (low to high)");
        $$(INVENTORY_ITEM_PRICE).first().shouldHave(text($_7_99));

        inventoryPage.sortBy("Price (high to low)");
        $$(INVENTORY_ITEM_PRICE).first().shouldHave(text($_49_99));
    }

    @Test
    @Description("Add items to cart")
    public void addItemToCartTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = loginPage.openPage()
                .login(STANDARD_USER, SECRET_SAUCE);

        inventoryPage
                .addItemToCart()
                .openCart();
        $$(CART_ITEM).shouldHave(CollectionCondition.size(1));
    }

    @Test
    @Description("Complete checkout and create order")
    public void completeCheckoutTest() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = loginPage.openPage()
                .login(STANDARD_USER, SECRET_SAUCE);

        inventoryPage
                .addItemToCart()
                .openCart()
                .proceedToCheckout()
                .enterShippingInformation(JOHN, "Doe", "12345")
                .completeOrder();

        $("#checkout_complete_container").shouldBe(visible).shouldHave(text("THANK YOU FOR YOUR ORDER"));
    }
}