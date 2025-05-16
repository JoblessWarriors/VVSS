package demo.pages;

import groovy.lang.Tuple;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage extends PageObject {
    @FindBy(css = "[data-test='inventory-item']")
    private List<WebElementFacade> shoppingCartItems;
    @FindBy(className = "cart_list")
    private WebElementFacade cartList;

    @FindBy(className = "cart_item")
    private List<WebElementFacade> cartItems;
    public void clickRemoveButton(int itemIndex){
        waitFor(shoppingCartItems.get(itemIndex));
        WebElementFacade item = shoppingCartItems.get(itemIndex);
        WebElementFacade removeButton = item.find(By.className("cart_button"));
        Assertions.assertEquals("Remove", removeButton.getText());
        removeButton.click();
    }

    public int getQuantityOfShoppingCart() {
        return shoppingCartItems.size();
    }

    public List<Tuple<String>> getShoppingCart() {
        List<Tuple<String>> items = new ArrayList<>();
        for (WebElementFacade shoppingCartItem : shoppingCartItems) {
            waitFor(shoppingCartItem);
            String itemName = shoppingCartItem.find(By.className("inventory_item_name")).getText();
            String price = shoppingCartItem.find(By.className("inventory_item_price")).getText();
            items.add(new Tuple<>(itemName, price));
        }
        return items;
    }


    public boolean isVisible() {
        return cartList.isVisible();
    }


}
