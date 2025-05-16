package demo.steps.serenity;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.ShoppingCartPage;
import demo.utils.Configuration;
import groovy.lang.Tuple;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.core.Serenity.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {
    @Managed(options = "start-maximized; disable-infobars; disable-save-password-bubble")
    public WebElement webdriver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ShoppingCartPage shoppingCartPage;

    @Step
    public void logsIn(String username, String password) {
        loginPage.open();
        loginPage.setUsernameField(username);
        loginPage.setPasswordField(password);
        loginPage.clickLoginButton();
    }

    @Step
    public void checkLoginSuccessful() {
        Assertions.assertTrue(homePage.isVisible());
        Assertions.assertEquals(Configuration.BASE_URL + "inventory.html", getDriver().getCurrentUrl());
    }

    @Step
    public void checkLoginFailed() {
        Assertions.assertTrue(loginPage.loginErrorMessageIsVisible());
    }
    @Step
    // In your EndUserSteps class, add a method:
    public void dismissAnyPopups() {
        try {
            // Use the webdriver directly rather than findBy
            WebElement popup = webdriver.findElement(By.xpath(".//button[contains(text(),'OK')]"));
            if (popup.isDisplayed()) {
                popup.click();
            }
        } catch (Exception e) {
            // Ignore if not found or not visible
        }
    }
    @Step
    public void sortItemsZToA() {
        homePage.sortItemsZToA();
    }


    @Step
    public void checkSortedZToA() {
        Assertions.assertTrue(homePage.isSortedByNameZToA());
    }

    @Step
    public void addItemToCart(int itemIndex) {
        homePage.clickAddToCartButtonOnItem(itemIndex);
    }

    @Step
    public void checkAddItemToCartSuccessful(int expectedBadgeCount) {
        navigateHome();
        Assertions.assertEquals(expectedBadgeCount, homePage.getNumberOfAddedItemsBadgeCount());
        homePage.clickShoppingCartButton();
        Assertions.assertEquals(expectedBadgeCount, shoppingCartPage.getQuantityOfShoppingCart());
    }

    @Step
    public void viewCart() {
        homePage.clickShoppingCartButton();
        Assertions.assertTrue(shoppingCartPage.isVisible());
    }

    @Step
    public void removeItemFromCart(int itemIndex) {
        shoppingCartPage.clickRemoveButton(itemIndex);
    }

    @Step
    public void checkItemRemovedFromCart(int expectedCount) {
        Assertions.assertEquals(expectedCount, shoppingCartPage.getQuantityOfShoppingCart());
    }

    @SafeVarargs
    public final void checkShoppingCart(Tuple<String>... expectedItems) {
        homePage.clickShoppingCartButton();

        // Only check the number of items if expectedItems are provided
        if (expectedItems != null && expectedItems.length > 0) {
            Assertions.assertEquals(expectedItems.length, shoppingCartPage.getQuantityOfShoppingCart());

            for (Tuple<String> expectedItem : expectedItems) {
                assertThat(shoppingCartPage.getShoppingCart(), hasItem(expectedItem));
            }
        }
    }

    @Step
    public void openBurgerMenu() {
        homePage.clickBurgerButton();
    }

    @Step
    public void logsOut() {
//        openBurgerMenu();
        homePage.clickLogoutButton();
    }



    @Step
    public void checkLogoutSuccessful() {
        Assertions.assertTrue(loginPage.isVisible());
        Assertions.assertEquals(Configuration.BASE_URL, getDriver().getCurrentUrl());
    }

    public void navigateHome(){
        homePage.clickAllItemsButton();
    }
}