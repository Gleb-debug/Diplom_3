package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    public final String REGISTER_PAGE = "https://stellarburgers.nomoreparties.site/register";

    @FindBy(xpath = "//p[contains(text(),'Конструктор')]")
    private WebElement constructorButton;

    @FindBy(xpath = "//p[contains(text(),'Лента Заказов')]")
    private WebElement ordersButton;

    @FindBy(xpath = "//div[@class='AppHeader_header__logo__2D0X2']")
    private WebElement logo;

    @FindBy(xpath = "//p[contains(text(),'Личный Кабинет')]")
    private WebElement personalAccountButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input")
    private WebElement nameField;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input")
    private WebElement emailField;

    @FindBy(name = "Пароль")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Зарегистрироваться')]")
    private WebElement registrationButton;

    @FindBy(xpath = "//a[contains(text(),'Войти')]")
    private WebElement loginLink;

    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    private WebElement incorrectPasswordError;

    public RegistrationPage() {
        PageFactory.initElements(driver, this);
    }

    public void waitPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
    }

    public void waitNameField() {
        wait.until(ExpectedConditions.elementToBeClickable(nameField));
    }

    public boolean incorrectPasswordErrorIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(incorrectPasswordError));
            return incorrectPasswordError.isDisplayed();
        } catch (TimeoutException e){
            return true;
        }
    }


    @Step("Кликаем по кнопке Зарегистрироваться")
    public void clickRegistrationButton() {
                registrationButton.click();
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public void openRegistrationPage() {
        driver.get(REGISTER_PAGE);
    }

    @Step("Кликаем по кнопке Войти")
    public void clickLoginLink() {
        loginLink.click();
    }

    @Step("Заполнение формы регистрации")
    public void fillRegistrationForm(String name, String email, String password) {
        nameField.sendKeys(name);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
    }
}