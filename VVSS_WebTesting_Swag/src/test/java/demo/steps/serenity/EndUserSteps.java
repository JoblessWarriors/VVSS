package demo.steps.serenity;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.ShoppingCartPage;
import demo.utils.Configuration;
import groovy.lang.Tuple;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {

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
        Assert.assertTrue(homePage.isVisible());
        Assert.assertEquals(Configuration.BASE_URL + "inventory.html", getDriver().getCurrentUrl());
    }

    @Step
    public void checkLoginFailed() {
        Assert.assertTrue(loginPage.loginErrorMessageIsVisible());
    }

    @Step
    public void sortItemsZToA() {
        homePage.sortItemsZToA();
    }

    @Step
    public void checkSortedZToA() {
        Assert.assertTrue(homePage.isSortedByNameZToA());
    }

    @Step
    public void addItemToCart(int itemIndex) {
        homePage.clickAddToCartButtonOnItem(itemIndex);
    }

    @Step
    public void checkAddItemToCartSuccessful(int expectedBadgeCount) {
        navigateHome();
        Assert.assertEquals(expectedBadgeCount, homePage.getNumberOfAddedItemsBadgeCount());
        homePage.clickShoppingCartButton();
        Assert.assertEquals(expectedBadgeCount, shoppingCartPage.getQuantityOfShoppingCart());
    }

    @Step
    public void viewCart() {
        homePage.clickShoppingCartButton();
        Assert.assertTrue(shoppingCartPage.isVisible());
    }

    @Step
    public void removeItemFromCart(int itemIndex) {
        shoppingCartPage.clickRemoveButton(itemIndex);
    }

    @Step
    public void checkItemRemovedFromCart(int expectedCount) {
        Assert.assertEquals(expectedCount, shoppingCartPage.getQuantityOfShoppingCart());
    }

    @SafeVarargs
    public final void checkShoppingCart(Tuple<String>... expectedItems){
        homePage.clickShoppingCartButton();
        Assert.assertEquals(expectedItems.length, shoppingCartPage.getQuantityOfShoppingCart());
        for (Tuple<String> expectedItem : expectedItems) {
            assertThat(shoppingCartPage.getShoppingCart(), hasItem(expectedItem));
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
        Assert.assertTrue(loginPage.isVisible());
        Assert.assertEquals(Configuration.BASE_URL, getDriver().getCurrentUrl());
    }

    public void navigateHome(){
        homePage.clickAllItemsButton();
    }
}