package demo.features.login;

import demo.steps.serenity.EndUserSteps;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;

@ExtendWith(SerenityJUnit5Extension.class)
public class LoginValidTest {
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
    public void test_login_with_valid_username_and_password(String username, String password) {
        user.logsIn(username, password);
        user.checkLoginSuccessful();
    }
}