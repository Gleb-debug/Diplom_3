import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.dto.CreateTestUser;
import pages.RegistrationPage;
import util.CreateUserAPI;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;

public class RegistrationPageTest extends BaseTest {
    private RegistrationPage registrationPage;
    private String accessToken;
    private CreateTestUser testUser;
    private CreateUserAPI createUserAPI;
    private LoginPage loginPage;

    @Before
    public void createTestUser() {
        testUser = createUserAPI.randomUser();
    }



    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void testSuccessfulRegistration() {
        loginPage = new LoginPage();
        registrationPage = new RegistrationPage();
        Allure.step("Открываем Страницу регистрации", () -> {
            registrationPage.openRegistrationPage();
            registrationPage.waitNameField();
        });

        Allure.step("Заполняем форму регистрации тестовыми данными", () -> {
            registrationPage.fillRegistrationForm(testUser.getName(), testUser.getEmail(), testUser.getPassword());
        });

        Allure.step("Проверяем, что после регистрации открылась страница входа", () -> {
            registrationPage.clickRegistrationButton();
            loginPage.waitUrlLoginPage();
            Assert.assertEquals("Должна открыться страница входа, но что-то пошло не так", loginPage.LOGIN_PAGE, loginPage.getCurrentUrl());
        });
    }

    @Test
    @DisplayName("Проверяем ошибку при вводе короткого пароля (5 символов)")
    public void testNotSuccessfulRegistrationPassword5() {
        loginPage = new LoginPage();
        registrationPage = new RegistrationPage();
        Allure.step("Открываем Страницу регистрации", () -> {
            registrationPage.openRegistrationPage();
            registrationPage.waitNameField();
        });

        Allure.step("Заполняем форму регистрации тестовыми данными", () -> {
            registrationPage.fillRegistrationForm(createUserAPI.randomUser().getName(), createUserAPI.randomUser().getEmail(), "P@ss1");
        });

        Allure.step("Ошибка \"Некорректный пароль\" отображается", () -> {
            registrationPage.clickRegistrationButton();
            Assert.assertTrue("Ожидаемая ошибка 'Некорректный пароль' не отображается", registrationPage.incorrectPasswordErrorIsDisplayed());
        });
    }

    @Test
    @DisplayName("Проверяем отсутствие ошибки при вводе допустимого пароля (6 символов)")
    public void testSuccessfulRegistrationPassword6(){
        registrationPage = new RegistrationPage();
        Allure.step("Открываем Страницу регистрации", () -> {
            registrationPage.openRegistrationPage();
            registrationPage.waitNameField();
        });

        Allure.step("Заполняем форму регистрации тестовыми данными", () -> {
            registrationPage.fillRegistrationForm(createUserAPI.randomUser().getName(), createUserAPI.randomUser().getEmail(), "P@ssw1");
        });

        Allure.step("Ошибка \"Некорректный пароль\" не отображается", () -> {
            Assert.assertTrue("Ошибка 'Некорректный пароль' отображается, хотя не должна", registrationPage.incorrectPasswordErrorIsDisplayed());
        });
    }

    @After
    public void deleteTestUser() {
        if (testUser != null) {
            accessToken = createUserAPI.logInAndGetToken(testUser);
            if (accessToken != null) {
                createUserAPI.deleteUser(accessToken).statusCode(HTTP_ACCEPTED);
                System.out.println("Тесты окончены, тестовый пользователь удалён");
            }
        }
    }
}