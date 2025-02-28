import io.github.bonigarcia.wdm.WebDriverManager;
import mainPage.HeaderPage;
import mainPage.AboutRentPage;
import mainPage.ForWhomPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;

// новое - второе окружение
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class makingOrderTest {
    private WebDriver chromeDriver; // изменено для Google Chrome
    private WebDriver firefoxDriver; // новое - для Mozilla Firefox driver;

    // новое - список для хранения ошибок
    private final List<String> errors = new ArrayList<>();

    @Before
    public void setUp() { // изменено
        WebDriverManager.chromedriver().setup(); // для Google Chrome
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();

        // новое - для Mozilla Firefox
        WebDriverManager.firefoxdriver().setup(); // для Mozilla Firefox
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        firefoxDriver = new FirefoxDriver(options);
    }

    @Test
    public void makingAnOrder() {
        // новое
        testInBrowser(chromeDriver, "Chrome");
        testInBrowser(firefoxDriver, "Firefox");
        // новое - вывод всех ошибок для браузеров после завершения теста
        if (!errors.isEmpty()) {
            throw new RuntimeException("Тест завершился с ошибками: " + errors);
        }
    }
    private void testInBrowser(WebDriver driver, String browserName) {
        try {
            // 1 сценарий: кнопка "Заказать" вверху
            executeScenario(driver, "top", new String[]{"Вася", "Васнецов", "Усачева, 3", "Пражская", "+79998883344", "14.03.2025", "двое суток", "black", "Больно, мне больно"});

            // 2 сценарий: кнопка "Заказать" в середине
            executeScenario(driver, "middle", new String[]{"Алиса", "Иванова", "Орлова, 11", "Лубянка", "+79998885544", "8.03.2025", "трое суток", "black", "Больно, мне больно"});
        } catch (Exception e) {
            errors.add("Ошибка в браузере " + browserName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    // метод выполнения сценариев
    private void executeScenario(WebDriver driver, String buttonPosition, String[] data) {
        try {
        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru");

        // создаем объект на главной странице
        HeaderPage objHeaderPage = new HeaderPage(driver);
        // клик по кнопке "Заказать"
        if ("top".equals(buttonPosition)) {
            objHeaderPage.clickOrderButtonTop();
        } else if ("middle".equals(buttonPosition)) {
            objHeaderPage.clickOrderButtonMiddle();
        }

        // создаем объект формы "Для кого"
        ForWhomPage objForWhomPage = new ForWhomPage(driver);

        // новое - проверка, что открылась страница "Для кого"
            Assert.assertTrue("Страница 'Для кого' не открылась", objForWhomPage.isForWhomPageDisplayed());

            objForWhomPage.completeTheFormForWhom(data[0], data[1], data[2], data[3], data[4]);
            objForWhomPage.clickNextButton();// клик по кнопке "Далее"

        // создаем объект формы Про аренду
        AboutRentPage objAboutRentPage = new AboutRentPage(driver);

            // изменено - проверка, что открылась страница "Про аренду"
            Assert.assertTrue("Страница 'Про аренду' не открылась", objAboutRentPage.isOrderHeaderDisplayed());

            objAboutRentPage.selectDate(data[5]);// выбрать дату
            objAboutRentPage.selectRentalPeriod(data[6]);// выбрать срок аренды
            objAboutRentPage.selectColor(data[7]);// выбрать цвет
            objAboutRentPage.writeComment(data[8]);// оставить комментарий
            objAboutRentPage.clickOrderButtonBottom();// сказать Заказать
            objAboutRentPage.giveConsent();// согласить оформить заказ
            objAboutRentPage.orderHasBeenPlaced();// заказ оформлен
        // новое
        } catch (Exception e) {
            errors.add("Ошибка в браузере " + driver.getClass().getSimpleName() + " при выполнении сценария с кнопкой '" + buttonPosition + "': " + e.getMessage());
            e.printStackTrace();
        }
    }
    @After
    public void teardown() {
        // закрыть браузер
            if (chromeDriver != null) { // изменено
                chromeDriver.quit();
            }
            if (firefoxDriver != null) { // новое
                firefoxDriver.quit();
            }
    }
}