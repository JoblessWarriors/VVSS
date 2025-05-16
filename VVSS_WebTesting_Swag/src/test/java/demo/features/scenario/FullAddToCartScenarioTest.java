package demo.features.scenario;

import demo.steps.serenity.EndUserSteps;
import groovy.lang.Tuple;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;

@ExtendWith(SerenityJUnit5Extension.class)
public class FullAddToCartScenarioTest {
    @Managed(options = "start-maximized; disable-infobars; disable-save-password-bubble")

    public WebDriver webdriver;

    @Steps
    public EndUserSteps user;

    @BeforeEach
    public void maximize() {
        webdriver.manage().window().maximize();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/features/login/valid_data_for_login.csv", numLinesToSkip = 1)
    public void test_valid_login_sort_add_check_cart_remove_logout(String username, String password) {
        // login
        user.logsIn(username, password);
        user.checkLoginSuccessful();

        // sort

        user.sortItemsZToA();
        user.checkSortedZToA();
        user.dismissAnyPopups();
        // add to cart
        user.addItemToCart(0);

        user.dismissAnyPopups();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        user.checkShoppingCart(CartItem_TestAll);

        // check cart
        user.viewCart();
        user.checkShoppingCart(CartItem_TestAll);

        // remove from cart
        user.removeItemFromCart(0);
        user.checkShoppingCart();

        // logout
        user.logsOut();
        user.checkLogoutSuccessful();

        // close browser
        webdriver.quit();
    }

    private static final Tuple<String> CartItem_TestAll = new Tuple<>("Test.allTheThings() T-Shirt (Red)", "$15.99");
}