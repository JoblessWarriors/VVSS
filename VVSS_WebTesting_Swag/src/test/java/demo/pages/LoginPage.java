package demo.pages;

import demo.utils.Configuration;
import net.serenitybdd.annotations.DefaultUrl;  // Updated import

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

@DefaultUrl(Configuration.BASE_URL)
public class LoginPage extends PageObject {
    @FindBy(id="user-name")
    private WebElementFacade usernameField;
    @FindBy(id="password")
    private WebElementFacade passwordField;
    @FindBy(id="login-button")
    private WebElementFacade loginButton;

    public void setUsernameField(String username) {
        waitFor(usernameField);
        typeInto(usernameField, username);
    }

    public void setPasswordField(String password) {
        waitFor(passwordField);
        passwordField.waitUntilClickable();
        typeInto(passwordField, password);
    }
    public void clickLoginButton() {
        loginButton.click();
    }

    public boolean loginErrorMessageIsVisible() {
        WebElementFacade loginErrorMessage = findBy("[data-test='error']");
        return loginErrorMessage.isVisible();
    }

    public boolean isVisible() {
        return usernameField.isCurrentlyVisible() && passwordField.isCurrentlyVisible() && loginButton.isCurrentlyVisible();
    }
}
