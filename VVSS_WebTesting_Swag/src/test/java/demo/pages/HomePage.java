package demo.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.util.List;

public class HomePage  extends PageObject {
    // for the elements on the page
    @FindBy(css = "[data-test='inventory_list']") // This selector is used to find the inventory items on the page
    private List<WebElementFacade> inventoryItems;

    // for shopping cart
    @FindBy(className = "shopping_cart_link") // This selector is used to find the shopping cart button
    private WebElementFacade shoppingCartButton;

    // for menu button
    @FindBy(id = "react-burger-menu-btn") // This selector is used to find the menu button
    private WebElementFacade menuButton;
    //for product sorter
    @FindBy(className = "product_sort_container") // This selector is used to find the product sorter
    private WebElementFacade productSorter;

    public boolean isVisible() {
        try {
            Thread.sleep(2000); // Sleep for 2 seconds to allow the page to load
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        waitFor(inventoryItems.get(0)); // Wait for the first inventory item to be visible
        return inventoryItems.get(0).isCurrentlyVisible(); // Check if the first inventory item is visible
    }

    // for sorting products
    public void selectProductSorter(String option) {
        waitFor(productSorter); // Wait for the product sorter to be visible
        productSorter.selectByVisibleText(option); // Select the specified option from the product sorter
    }

    public void clickAddToCartButtonOnItem(int itemIndex) {
        waitFor(inventoryItems.get(itemIndex)); // Wait for the inventory item at the specified index to be visible
        WebElementFacade firstItem = inventoryItems.get(itemIndex); // Get the inventory item at the specified index
        WebElementFacade addToCartButton = firstItem.findBy(".btn_inventory"); // Find the "Add to Cart" button within the inventory item
        addToCartButton.click(); // Click the "Add to Cart" button
    }


    public int getNumberOfAddedItemsBadgeCount() {
        WebElementFacade shoppingCartContainer = findBy(".shopping_cart_container"); // Find the shopping cart container
        if(!shoppingCartContainer.containsElements(By.className("shopping_cart_badge"))) // Check if the shopping cart badge is present
            return 0; // If not present, return 0
        WebElementFacade shoppingCartBadge = findBy(".shopping_cart_badge"); // Find the shopping cart badge

        String badgeText = shoppingCartBadge.getText(); // Get the text of the shopping cart badge
        return Integer.parseInt(badgeText); // Parse and return the badge text as an integer
    }

    public void clickShoppingCartButton() {
        shoppingCartButton.click(); // Click the shopping cart button
    }
    public void clickLogoutButton() {
        menuButton.click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap")));
        WebElementFacade menuWrap = find(By.className("bm-menu-wrap"));
        WebElementFacade logoutButton = menuWrap.find(By.id("logout_sidebar_link"));
        logoutButton.click();
    }

    public void clickAllItemsButton(){
        menuButton.click(); // Click the menu button
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap"))); // Wait for the menu to be visible
        WebElementFacade menuWrap = find(By.className("bm-menu-wrap")); // Find the menu wrap
        WebElementFacade allItemsButton = menuWrap.find(By.id("inventory_sidebar_link")); // Find the "All Items" button
        allItemsButton.click(); // Click the "All Items" button
    }
    public boolean isSortedByNameZToA() {
        List<WebElementFacade> itemNames = findAll(By.className("inventory_item_name"));

        for (int i = 0; i < itemNames.size() - 1; i++) {
            if (itemNames.get(i).getText().compareTo(itemNames.get(i + 1).getText()) < 0) {
                return false;
            }
        }
        return true;
    }
    public void clickBurgerButton() {
        menuButton.click(); // Click the menu button
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap"))); // Wait for the menu to be visible
        WebElementFacade menuWrap = find(By.className("bm-menu-wrap")); // Find the menu wrap
        WebElementFacade allItemsButton = menuWrap.find(By.id("inventory_sidebar_link")); // Find the "All Items" button
    }
    public void sortItemsZToA() {
        selectProductSorter("Name (Z to A)");
    }

}
