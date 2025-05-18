package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainPage extends BasePage{
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    public final String MAIN_PAGE = "https://stellarburgers.nomoreparties.site/";


    @FindBy(xpath = "//p[contains(text(),'Конструктор')]")
    private WebElement constructorButton;

    @FindBy(xpath = "//p[contains(text(),'Лента Заказов')]")
    private WebElement ordersButton;

    @FindBy(xpath = "//div[@class='AppHeader_header__logo__2D0X2']")
    private WebElement logo;

    @FindBy(xpath = "//p[contains(text(),'Личный Кабинет')]")
    private WebElement personalAccountButton;

    @FindBy(xpath = "//button[contains(text(),'Войти в аккаунт')]")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@style='display: flex;']")
    private WebElement buttonsMenu;

    @FindBy(xpath = "//span[contains(text(),'Булки')]/..")
    private WebElement bunsSectionButton;

    @FindBy(xpath = "//span[contains(text(),'Соусы')]")
    private WebElement saucesSectionButton;

    @FindBy(xpath = "//span[contains(text(),'Начинки')]")
    private WebElement fillingsSectionButton;

    @FindBy(xpath = "//h2[contains(text(),'Булки')]")
    private WebElement bunsTitle;

    @FindBy(xpath = "//h2[contains(text(),'Соусы')]")
    private WebElement saucesTitle;

    @FindBy(xpath = "//h2[contains(text(),'Начинки')]")
    private WebElement fillingsTitle;

    public MainPage() {
        PageFactory.initElements(driver, this);
    }

    @Step("Кликаем по кнопке конструктор")
    public void clickConstructor() {
        constructorButton.click();
    }

    // Метод для клика по логотипу Stellar Burgers
    @Step("Кликаем по по логотипу Stellar Burgers")
    public void clickLogo() {
        logo.click();
    }

    // Метод для клика по кнопке Личный кабинет
    @Step("Кликаем по кнопке Личный кабинет")
    public void clickPersonalAccountButton() {
        personalAccountButton.click();
    }

    // Метод для клика по кнопке войти в аккаунт
    @Step("Кликаем по кнопке войти в аккаунт")
    public void clickButtonLogin() {
        loginButton.click();
    }

    // Метод для клика по кнопке Булки
    @Step("Кликаем по кнопке Булки")
    public void clickButtonBuns() {
        bunsSectionButton.click();
    }

    // Метод для клика по кнопке Соусы
    @Step("Кликаем по кнопке Соусы")
    public void clickButtonSauce() {
        saucesSectionButton.click();
    }

    // Метод для клика по кнопке Начинки
    @Step("Кликаем по кнопке Начинки")
    public void clickButtonFilling() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsSectionButton)).click();
    }

    // Метод для возвращения локатора кнопки войти
    public void waitButtonLoginLocator(){
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    }

    // Метод для возвращения локатора кнопки Личный кабинет
    public void waitPersonalAccountButtonLocator(){
        wait.until(ExpectedConditions.visibilityOf(personalAccountButton));
    }

    // Метод для возвращения локатора логотипа
    public void waitLogoLocator(){
        wait.until(ExpectedConditions.visibilityOf(logo));
    }

    @Step("Возвращаем координаты заголовков")
    public List<Integer> getTitleCoordinates(String title) {
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(buttonsMenu.getLocation().getY() + buttonsMenu.getSize().getHeight());
        switch (title) {
            case "начинки" :
                clickButtonFilling();
                wait.until(driver -> fillingsTitle.getLocation().getY() == (buttonsMenu.getLocation().getY() + buttonsMenu.getSize().getHeight()));
                coordinates.add(fillingsTitle.getLocation().getY());
                break;
            case "соусы" :
                clickButtonSauce();
                wait.until(driver -> saucesTitle.getLocation().getY() == (buttonsMenu.getLocation().getY() + buttonsMenu.getSize().getHeight()));
                coordinates.add(saucesTitle.getLocation().getY());
                break;
            case "булки" :
                clickButtonSauce();
                clickButtonBuns();
                wait.until(driver -> bunsTitle.getLocation().getY() == (buttonsMenu.getLocation().getY() + buttonsMenu.getSize().getHeight()));
                coordinates.add(bunsTitle.getLocation().getY());
                break;
            default :
                System.out.println("Unknown title: ");
        }
        return coordinates;
    }

    public void waitUrlMainPage(){
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
    }

    public void openMainPage(){
        driver.get(MAIN_PAGE);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
