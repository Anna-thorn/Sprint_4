package mainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForWhomPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // конструктор
    public ForWhomPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);// явное ожидание
    }
    // локаторы
    private final By orderHeaderForWhom = By.xpath("//div[text()='Для кого самокат']");
    // поле Имя
    public By nameField = By.xpath("//input[@placeholder='* Имя']");
    // поле Фамилия
    public By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    // поле Адрес: куда привезти самокат
    public By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // поле Станция метро и список станций
    public By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    // изменено - поле Телефон: на него позвонит курьер
    public By phoneInputField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // изменено - кнопка Далее
    public By nextStepButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");

    // методы
    public boolean isForWhomPageDisplayed() {
        return driver.findElement(orderHeaderForWhom).isDisplayed();
    }
    // метод заполняет поле Имя
    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }
    // метод заполняет поле Фамилия
    public void setSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }
    // метод заполняет поле Адрес
    public void setAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }
    // новое - локатор для выбора станции метро в выпадающем списке
    public By metroStationOption(String stationName) {
        return By.xpath("//div[contains(@class, 'Order_Text__2broi') and text()='" + stationName + "']");
    }
    // метод заполняет поле Станция метро
    public void selectMetroStation(String stationName) {
        WebElement metroInput = driver.findElement(metroField);
        metroInput.click();
        metroInput.sendKeys(stationName);
        // изменено
        WebElement stationElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(metroStationOption(stationName))
        );
        stationElement.click();
    }
    // метод заполняет поле Телефон
    public void setTelephone(String telephone) {
        driver.findElement(phoneInputField).sendKeys(telephone);
    }
    // объединяющий метод для заполнения формы "Для кого"
    public void completeTheFormForWhom(String name, String surname, String address, String stationName, String telephone) {
        setName(name);
        setSurname(surname);
        setAddress(address);
        selectMetroStation (stationName);
        setTelephone(telephone);
    }
    // клик по кнопке Далее
    public void clickNextButton() {
       driver.findElement(nextStepButton).click();
    }
}