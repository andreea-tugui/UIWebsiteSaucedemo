package com.WebUI.Saucedemo;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class ProductListTest {

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        Configuration.baseUrl = "https://www.saucedemo.com";
        Configuration.browser = "chrome";
       // LoginTest loginTest = new LoginTest();
        open("/");
        LoginTest.login("standard_user", "secret_sauce");
    }

    @Test
    @Description("Sort Product list- Price Low to High")
    public void sortProductsPriceLowToHigh() {
        open("/inventory.html");
        sortProducts("lohi");
        $$(".inventory_item").first().shouldHave(Condition.text("Sauce Labs Onesie"));
    }

    @Test
    @Description("Sort Product list- Price High to Low")
    public void sortProductsPriceHighToLow() {
        open("/inventory.html");
        sortProducts("hilo");
        $$(".inventory_item").first().shouldHave(Condition.text("Sauce Labs Fleece Jacket"));
    }

    @Step("Sort products by {sortOption}")
    private void sortProducts(String sortOption) {
        $(".product_sort_container").selectOptionByValue(sortOption);
    }
}
