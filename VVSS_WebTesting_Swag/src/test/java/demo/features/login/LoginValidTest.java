package demo.features.login;

import demo.steps.serenity.EndUserSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.runner.RunWith;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("features/login/valid_data_for_login.csv")
public class LoginValidTest {
     @Managed(uniqueSession = true)
     public WebDriver webdriver;
     @Steps
     public EndUserSteps user;
     public String username, password;
     @Before
     public void maximize() {
         webdriver.manage().window().maximize();
     }
     @Test
     //@Ignore
     public void test_login_with_valid_username_and_password() {
         user.logsIn(username,password);
         user.checkLoginSuccessful();
     }
}
