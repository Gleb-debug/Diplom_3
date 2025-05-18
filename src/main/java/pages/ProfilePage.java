package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage extends BasePage {

    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    public final String PROFILE_PAGE = "https://stellarburgers.nomoreparties.site/account/profile";

    // Локатор для кнопки Выход
    @FindBy(xpath = "//button[contains(text(),'Выход')]")
    private WebElement loginOutButton;

    public ProfilePage() {
        PageFactory.initElements(driver, this);
    }

    // Метод для клика по кнопке Выход
    @Step("Клик по кнопке выход")
    public void logoutButtonClick() {
        loginOutButton.click();
    }

    // Метод для возвращения локатора кнопки Выход
    public void waitLogoutButtonLocator() {
        wait.until(ExpectedConditions.visibilityOf(loginOutButton));
    }

    public void waitUrl(){
        wait.until(ExpectedConditions.urlToBe(PROFILE_PAGE));
    }

}
