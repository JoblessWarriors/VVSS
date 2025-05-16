package demo.features.scenario;


import demo.steps.serenity.EndUserSteps;
import groovy.lang.Tuple;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("features/login/valid_data_for_login.csv")
public class FullAddToCartScenarioTest {
    @Managed(uniqueSession = true, driver="firefox")
    public WebDriver webdriver;

    @Steps
    public EndUserSteps user;

    public String username, password;

    @Before
    public void maximize() {
        webdriver.manage().window().maximize();
    }

    @Test
    public void test_valid_login_sort_add_check_cart_remove_logout() {
        // login
        user.logsIn(username, password);
        user.checkLoginSuccessful();


        // sort
        user.sortItemsZToA();
        user.checkSortedZToA();

        // add to cart
        user.addItemToCart(0);
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


    private static final Tuple<String> CartItem_TestAll=new Tuple<>("Test.allTheThings() T-Shirt (Red)","$15.99");


}
