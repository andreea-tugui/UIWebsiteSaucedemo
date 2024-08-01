package com.webui.saucedemo;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class CheckoutTest {

    @BeforeAll
    public static void setup() {
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
        Configuration.baseUrl = "https://www.saucedemo.com";
       // Configuration.browser = "chrome";
        open("/");
        LoginTest.login("standard_user", "secret_sauce");  // Assuming static login for simplicity
    }

    @Test
    @Description("Add item to cart")
    public void addItemToCart() {
        open("/inventory.html");
        addItemToCart("Sauce Labs Backpack");
        $(".shopping_cart_badge").shouldHave(Condition.text("1"));
    }

    @Test
    @Description("Complete checkout and create order")
    public void completeCheckout() {
        addItemToCart("Sauce Labs Backpack");
        open("/cart.html");
        $("#checkout").click();
        $("#first-name").setValue("John");
        $("#last-name").setValue("Doe");
        $("#postal-code").setValue("12345");
        $("#continue").click();
        $("#finish").click();
        $(".complete-header").shouldHave(Condition.text("THANK YOU FOR YOUR ORDER"));
    }

    @Step("Add item to cart: {itemName}")
    private void addItemToCart(String itemName) {
        $$(".inventory_item").findBy(Condition.text(itemName)).$(".btn_inventory").click();
    }
}
