import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import pages.dto.CreateTestUser;
import util.CreateUserAPI;

import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;

public class LoginPageTest extends BaseTest {
    private CreateTestUser testUser;
    private CreateUserAPI createUserAPI;
    private String accessToken;
    protected WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private ForgetPage forgetPage;


    @Before
    public void createTestUser() {
        testUser = createUserAPI.randomUser();
        accessToken = createUserAPI.createUser(testUser);
    }


    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void testSuccessfulLoginMainPage() {
        loginPage = new LoginPage();
        mainPage = new MainPage();
        Allure.step("Открываем Главную страницу сайта", () -> {
            mainPage.openMainPage();
        });

        Allure.step("Нажимаем на кнопку «Войти в аккаунт»", () -> {
            mainPage.waitButtonLoginLocator();
            mainPage.clickButtonLogin();
        });

        Allure.step("Ожидаем загрузки страницы входа", loginPage::waitLoginButtonLocator);

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

          Allure.step("Проверяем, что после входа открылась главная страница", () -> {
              mainPage.waitUrlMainPage();
            Assert.assertEquals("Должна открыться главная страница, но что-то пошло не так", mainPage.MAIN_PAGE, mainPage.getCurrentUrl());
        });
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void testSuccessfulLoginForPersonalAccountButton() {
        loginPage = new LoginPage();
        mainPage = new MainPage();
        Allure.step("Открываем Главную страницу сайта", () -> {
            mainPage.openMainPage();
        });

        Allure.step("Кликаем по кнопке Личный кабинет", () -> {
            mainPage.waitPersonalAccountButtonLocator();
            mainPage.clickPersonalAccountButton();
        });

        Allure.step("Ожидаем загрузки страницы входа", loginPage::waitLoginButtonLocator);

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Проверяем, что после входа открылась главная страница", () -> {
            mainPage.waitUrlMainPage();
            Assert.assertEquals("Должна открыться главная страница, но что-то пошло не так", mainPage.MAIN_PAGE, mainPage.getCurrentUrl());
        });
    }

    @Test
    @DisplayName("вход через кнопку в форме регистрации")
    public void testSuccessfulLoginFromButtonInRegistrationPage() {
        loginPage = new LoginPage();
        registrationPage = new RegistrationPage();
        mainPage = new MainPage();
        Allure.step("Открываем страницу регистрации", () -> {
        registrationPage.openRegistrationPage();
        registrationPage.waitPersonalAccountButton();
        });

        Allure.step("Кликаем по ссылке Войти", registrationPage::clickLoginLink);

        Allure.step("Ожидаем загрузку страницы входа", loginPage::waitLoginButtonLocator);

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Проверяем, что после входа открылась главная страница", () -> {
            mainPage.waitUrlMainPage();
            Assert.assertEquals("Должна открыться главная страница, но что-то пошло не так", mainPage.MAIN_PAGE, mainPage.getCurrentUrl());
        });
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testSuccessfulLoginFromButtonInForgetPage() {
        forgetPage = new ForgetPage();
        loginPage = new LoginPage();
        mainPage = new MainPage();

        Allure.step("Открываем страницу Восстановления пароля", () -> {
            forgetPage.openForgetPage();
            forgetPage.waitLoginLinkLocator();
        });

        Allure.step("Кликаем по ссылке Войти", forgetPage::loginLinkClick);

        Allure.step("Ожидаем загрузку страницы входа", loginPage::waitLoginButtonLocator);

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Проверяем, что после входа открылась главная страница", () -> {
            mainPage.waitUrlMainPage();
            Assert.assertEquals("Должна открыться главная страница, но что-то пошло не так", mainPage.MAIN_PAGE, mainPage.getCurrentUrl());
        });
    }

    @After
    public void deleteUser() {
        if (testUser != null && accessToken != null) {
            createUserAPI.deleteUser(accessToken).statusCode(HTTP_ACCEPTED);
        }
    }
}