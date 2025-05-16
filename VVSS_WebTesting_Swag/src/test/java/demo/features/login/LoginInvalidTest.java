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
public class LoginInvalidTest {
    @Managed(options = "start-maximized; disable-infobars; disable-save-password-bubble")
    public WebDriver webdriver;

    @Steps
    public EndUserSteps user;

    private String username;
    private String password;

    @BeforeEach
    public void maximize() {
        webdriver.manage().window().maximize();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/features/login/invalid_data_for_login.csv", numLinesToSkip = 1)
    public void test_login_with_invalid_username_and_password(String username, String password) {
        this.username = username;
        this.password = password;
        user.logsIn(username, password);
        user.checkLoginFailed();
    }
}