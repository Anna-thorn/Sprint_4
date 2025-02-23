package mainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHeader {
    private final WebDriver driver;
    // конструктор
    public PageHeader(WebDriver driver) {
        this.driver = driver;
    }

    // локаторы
    // кнопка "Заказать" в верхней части страницы
    private final By orderButtonTop = By.className("Button_Button__ra12g");

    // методы
    // клик по кнопке "Заказать"
    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }
}