package com.webui.saucedemo.pages;


import com.codeborne.selenide.SelenideElement;
import com.webui.saucedemo.utils.PageConstants;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {


    private final SelenideElement checkoutButton = $(PageConstants.CHECKOUT);
    private final SelenideElement cartButton = $(PageConstants.SHOPPING_CART_LINK);

    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        checkoutButton.shouldBe(visible).click();
        return new CheckoutPage();
    }

    @Step("Open cart")
    public CartPage openCart() {
        cartButton.shouldBe(visible).click();
        return new CartPage();
    }

}
