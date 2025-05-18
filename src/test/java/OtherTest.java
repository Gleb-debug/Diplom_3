import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import pages.dto.CreateTestUser;
import util.CreateUserAPI;

import java.time.Duration;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;

public class OtherTest extends BaseTest {

    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private CreateTestUser testUser;
    private CreateUserAPI createUserAPI;
    private String accessToken;

    @Before
    public void createTestUser() {
        testUser = createUserAPI.randomUser();
        accessToken = createUserAPI.createUser(testUser);
    }



    @Test
    @DisplayName("Переход по клику на «Личный кабинет».")
    public void testPersonalAccount() {
        mainPage = new MainPage();
        loginPage = new LoginPage();
        profilePage = new ProfilePage();

        Allure.step("Открываем страницу входа", () -> {
            driver.get(loginPage.LOGIN_PAGE);
            loginPage.waitLoginButtonLocator();
        });

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Ожидаем загрузку главной страницы", () -> {
            mainPage.waitPersonalAccountButtonLocator();
        });

        Allure.step("Кликаем по кнопке Личный кабинет", () -> {
            mainPage.clickPersonalAccountButton();;
        });

        Allure.step("Проверяем, что открылась страница Личного кабинета", () -> {
            profilePage.waitUrl();
            Assert.assertEquals("Должна открыться страница входа, но что-то пошло не так", profilePage.PROFILE_PAGE, driver.getCurrentUrl());;
        });
    }

    @Test
    @DisplayName("Переход по клику на «Конструктор» из личного кабинета")
    public void testConstructorButtonFromPersonalAccount() {
        mainPage = new MainPage();
        loginPage = new LoginPage();

        Allure.step("Открываем страницу входа", () -> {
            driver.get(loginPage.LOGIN_PAGE);
            loginPage.waitLoginButtonLocator();
        });

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Ожидаем загрузку главной страницы", () -> {
            mainPage.waitPersonalAccountButtonLocator();
        });

        Allure.step("Кликаем по кнопке Личный кабинет", () -> {
            mainPage.clickPersonalAccountButton();;
        });

        Allure.step("Кликаем по кнопке Конструктор", () -> {
            mainPage.waitPersonalAccountButtonLocator();
            mainPage.clickConstructor();
        });

        Allure.step("Проверяем что открылась страница конструктора", () -> {
            mainPage.waitUrlMainPage();
            Assert.assertEquals("Должна открыться страница конструктора, но что-то пошло не так", mainPage.MAIN_PAGE, driver.getCurrentUrl());
        });
    }

    @Test
    @DisplayName("Переход по клику на логотип Stellar Burgers из личного кабинета")
    public void testLogoFromPersonalAccount() {
        mainPage = new MainPage();
        loginPage = new LoginPage();

        Allure.step("Открываем страницу входа", () -> {
            driver.get(loginPage.LOGIN_PAGE);
            loginPage.waitLoginButtonLocator();
        });

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Ожидаем загрузку главной страницы", () -> {
            mainPage.waitPersonalAccountButtonLocator();
        });

        Allure.step("Кликаем по кнопке Личный кабинет", () -> {
            mainPage.clickPersonalAccountButton();;
        });

        Allure.step("Кликаем по логотипу", () -> {
            mainPage.waitLogoLocator();
            mainPage.clickLogo();
        });

        Allure.step("Проверяем, что открылась страница Конструктора", () -> {
            mainPage.waitUrlMainPage();
            Assert.assertEquals("Должна открыться страница конструктора, но что-то пошло не так", mainPage.MAIN_PAGE, driver.getCurrentUrl());
        });
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void testLogoutButtonFromPersonalAccount() {
        mainPage = new MainPage();
        loginPage = new LoginPage();
        profilePage = new ProfilePage();

        Allure.step("Открываем страницу входа", () -> {
            driver.get(loginPage.LOGIN_PAGE);
            loginPage.waitLoginButtonLocator();
        });

        Allure.step("Вводим Email и пароль тестового пользователя", () -> {
            loginPage.login(testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Ожидаем загрузку главной страницы", () -> {
            mainPage.waitPersonalAccountButtonLocator();
        });

        Allure.step("Кликаем по кнопке Личный кабинет", () -> {
            mainPage.clickPersonalAccountButton();;
        });

        Allure.step("Кликаем по кнопке Выход", () -> {
            profilePage.waitLogoutButtonLocator();
            profilePage.logoutButtonClick();
        });

        Allure.step("Проверяем, что открылась страница входа", () -> {
            loginPage.waitUrlLoginPage();
            Assert.assertEquals("Должна открыться страница входа, но что-то пошло не так", loginPage.LOGIN_PAGE, driver.getCurrentUrl());
        });

        Allure.step("Кликаем по кнопке Личный кабинет", () -> {
            mainPage.clickPersonalAccountButton();
        });

        Allure.step("Проверяем, что личный кабинет не доступен, пользователь вышел из системы", () -> {
            loginPage.waitUrlLoginPage();
            Assert.assertEquals("Должна открыться страница входа, но что-то пошло не так", loginPage.LOGIN_PAGE, driver.getCurrentUrl());
        });
    }


    @Test
    @DisplayName("Проверяем, что работают переходы к разделам начинки")
    public void testScrollBetweenSectionsFilling() {
        mainPage = new MainPage();
        Allure.step("Открываем страницу Конструктора", () -> {
            driver.get(mainPage.MAIN_PAGE);
        });

        // Кликаем по кнопке "Начинки" и проверяем координаты
        Allure.step("Кликаем по кнопке Начинки и проверяем координаты", () -> {
            List<Integer> coordinates = mainPage.getTitleCoordinates("начинки");
            Assert.assertEquals("Координаты заголовка 'Начинки' не совпадают с координатами панели кнопок после клика",
                    coordinates.get(0), coordinates.get(1));
        });
    }

    @Test
    @DisplayName("Проверяем, что работают переходы к разделам соусы")
    public void testScrollBetweenSectionsSauces() {
        mainPage = new MainPage();
        Allure.step("Открываем страницу Конструктора", () -> {
            driver.get(mainPage.MAIN_PAGE);
        });
        Allure.step("Кликаем по кнопке Соусы и проверяем координаты", () -> {
            List<Integer> coordinates = mainPage.getTitleCoordinates("соусы");
            Assert.assertEquals("Координаты заголовка 'Соусы' не совпадают с координатами панели кнопок после клика",
                    coordinates.get(0), coordinates.get(1));
        });
    }

    @Test
    @DisplayName("Проверяем, что работают переходы к разделам булки")
    public void testScrollBetweenSectionsBuns() {
        mainPage = new MainPage();
        Allure.step("Открываем страницу Конструктора", () -> {
            driver.get(mainPage.MAIN_PAGE);
        });
        Allure.step("Кликаем по кнопке Булки и проверяем координаты", () -> {
            List<Integer> coordinates = mainPage.getTitleCoordinates("булки");
            Assert.assertEquals("Координаты заголовка 'Булки' не совпадают с координатами панели кнопок после клика",
                    coordinates.get(0), coordinates.get(1));
        });
    }

    @After
    public void deleteUser() {
        if (testUser != null && accessToken != null) {
            createUserAPI.deleteUser(accessToken).statusCode(HTTP_ACCEPTED);
        }
    }
}
