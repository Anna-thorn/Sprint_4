import io.github.bonigarcia.wdm.WebDriverManager;
import mainPage.PageHeader;
import mainPage.TypeAboutRent;
import mainPage.TypeForWhom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.junit.Assert;

public class CompleteTheFormForWhomTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void makingAnOrder() {
        // массив с тестовыми данными
        String[][] testData = {
                {"Вася", "Васнецов", "Усачева, 3", "Пражская", "+79998883344", "14.03.2025", "двое суток", "black"},
                {"Алиса", "Иванова", "Орлова, 11", "Лубянка", "+79998885544", "8.03.2025", "трое суток", "black"},
        };
        // выполнение шагов для каждого набора
        for (String[] data : testData) {
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru");

        // создаем объект на главной странице
        PageHeader objPageHeader = new PageHeader(driver);
            objPageHeader.clickOrderButtonTop(); // клик по кнопке "Заказать"

        // создаем объект формы "Для кого"
        TypeForWhom objTypeForWhom = new TypeForWhom(driver);
            objTypeForWhom.completeTheFormForWhom(data[0], data[1], data[2], data[3], data[4]);
            objTypeForWhom.clickNextButton();// клик по кнопке "Далее"

        // проверка успешного перехода к следующей форме
        Assert.assertTrue("Переход не выполнен",
                driver.findElement(By.className("Order_Header__BZXOb")).isDisplayed());

        // создаем объект формы Про аренду
        TypeAboutRent objTypeAboutRent = new TypeAboutRent(driver);
            objTypeAboutRent.selectDate(data[5]);// выбрать дату
            objTypeAboutRent.selectRentalPeriod(data[6]);// выбрать срок аренды
            objTypeAboutRent.selectColor(data[7]);// выбрать цвет
            objTypeAboutRent.writeComment();// оставить комментарий
            objTypeAboutRent.clickOrderButtonBottom();// сказать Заказать
            objTypeAboutRent.giveConsent();// согласить оформить заказ
            objTypeAboutRent.orderHasBeenPlaced();// заказ оформлен
        }
    }
    @After
    public void teardown() {
        // закрыть браузер
        if (driver != null) {
            driver.quit();
        }
    }
}