package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement errorButton = $("[class=icon-button__content]");

    public void errorLogin() {
        errorNotification.shouldBe(visible);
    }

    public VerificationPage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }

    public void clearInput() {
        errorNotification.should(visible);
        errorButton.should(visible);
        errorButton.click();
        errorNotification.should(hidden);
        loginField.click();
        loginField.sendKeys(Keys.chord(Keys.CONTROL + "a"), Keys.BACK_SPACE);
        passwordField.click();
        passwordField.sendKeys(Keys.chord(Keys.CONTROL + "a"), Keys.BACK_SPACE);
    }
}