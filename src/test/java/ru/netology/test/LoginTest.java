package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    LoginPage loginPage = new LoginPage();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void cleanAll() {
        SQLHelper.cleanData();
    }

    @Test
    void shouldLogin() {
        var authInfo = DataHelper.authWithTestUser();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verificationPage.isVisibleVerificationPage();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotLoginIfUserRandom() {
        var authInfo = DataHelper.generateRandomUser();

        loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        loginPage.errorLogin();
    }

    @Test
    void shouldNotLoginIfCodeRandom() {
        var authInfo = DataHelper.authWithTestUser();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verificationPage.isVisibleVerificationPage();
        var verificationCode = DataHelper.randomCode();
        verificationPage.getVerificationCode(verificationCode);
        verificationPage.errorMessInvalidVerificationCode();
    }

    @Test
    public void blockIfPassIncorrect() {
        var authInfo = DataHelper.authWithTestUser();

        loginPage.validLogin(authInfo.getLogin(), DataHelper.randomPass());
        loginPage.clearInput();

        loginPage.validLogin(authInfo.getLogin(), DataHelper.randomPass());
        loginPage.clearInput();

        loginPage.validLogin(authInfo.getLogin(), DataHelper.randomPass());
        loginPage.clearInput();

        DataHelper.assertStatus(authInfo.getLogin());
    }
}