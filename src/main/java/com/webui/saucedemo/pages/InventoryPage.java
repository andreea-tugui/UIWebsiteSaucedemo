package com.webui.saucedemo.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.webui.saucedemo.utils.PageConstants;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class InventoryPage {

    private final SelenideElement sortDropdown = $(PageConstants.PRODUCT_SORT_CONTAINER);
    private final SelenideElement firstItemAddToCartButton = $$(PageConstants.INVENTORY_ITEM).first().$(".btn_inventory");

    @Step("Sort products by {option}")
    public InventoryPage sortBy(String option) {
        sortDropdown.shouldBe(visible).selectOption(option);
        return this;
    }

    @Step("Add item to cart")
    public CartPage addItemToCart() {
        firstItemAddToCartButton.shouldBe(visible).click();
        return new CartPage();
    }

}