package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    public final String LOGIN_PAGE = "https://stellarburgers.nomoreparties.site/login";



    // Локатор для кнопки Конструктор
    @FindBy(xpath = "//a[@class='AppHeader_header__link__3D_hX AppHeader_header__link_active__1IkJo']")
    private WebElement constructor;
    // Локатор для кнопки Лента заказов
    @FindBy(linkText = "feed")
    private WebElement orders;
    // Локатор для логотипа
    @FindBy(className = "active")
    private WebElement logo;
    // Локатор для кнопки Личный кабинет
    @FindBy(linkText = "account")
    private WebElement personalAccountButton;

    // Локатор для поля ввода email
    @FindBy(name = "name")
    private WebElement emailField;
    // Локатор для поля ввода пароля
    @FindBy(name = "Пароль")
    private WebElement passwordField;
    // Локатор для кнопки Войти в аккаунт
    @FindBy(xpath = "//button[contains(text(),'Войти')]" )
    private WebElement loginButton;


    // Локатор для ссылки Зарегистрироваться
    @FindBy(xpath = "//a[contains(text(),'Зарегистрироваться')]")
    private WebElement registrationLink;
    // Локатор для ссылки Восстановить Пароль
    @FindBy(xpath = "//a[contains(text(),'Восстановить пароль')]")
    private WebElement restorePasswordLink;

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public void waitLoginButtonLocator() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
    }

    // Метод для входа в систему
    public void login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        Allure.step("Кликаем кнопку Вход", () -> {
            loginButton.click();
        });

    }
    public void waitUrlLoginPage() {
        wait.until(ExpectedConditions.urlToBe(LOGIN_PAGE));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}