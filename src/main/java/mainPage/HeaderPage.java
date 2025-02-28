package mainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class HeaderPage {
    private final WebDriver driver;
    // конструктор
    public HeaderPage(WebDriver driver) {
        this.driver = driver;
    }

    // локаторы
    // изменено - кнопка "Заказать" в верхней части страницы
    private final By orderButtonTop = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and text()='Заказать']");

    // новое - кнопка "Заказать" в середине страницы
    private final By orderButtonMiddle = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    // логотип Самокат
    public By scooterLogo = By.xpath("//img[@alt='Scooter']");
    // логотип Яндекс
    public By yandexLogo = By.xpath("//img[@alt='Yandex']");

    // методы
    // клик по кнопке "Заказать" в верхней части страницы
    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }
    // новое - прокручивание и клик по кнопке "Заказать" в середине страницы
    public void clickOrderButtonMiddle() {
        WebElement element = driver.findElement(orderButtonMiddle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }
}