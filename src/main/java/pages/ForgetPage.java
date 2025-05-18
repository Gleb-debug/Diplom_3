package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgetPage extends BasePage{

    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    public final static String FORGET_PAGE = "https://stellarburgers.nomoreparties.site/forgot-password";

    // Локатор для ссылки Войти
    @FindBy(xpath = "//a[@class='Auth_link__1fOlj']")
    private WebElement loginLink;

    public ForgetPage() {
        PageFactory.initElements(driver, this);
    }


    @Step("Клик по войти в аккаунт")
    public void loginLinkClick() {
        loginLink.click();
    }


    public void waitLoginLinkLocator() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink));
    }

    @Step("Открываем страницу 'Забыли пароль'")
    public void openForgetPage() {
        driver.get(FORGET_PAGE);
    }

}
