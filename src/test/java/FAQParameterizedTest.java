import io.github.bonigarcia.wdm.WebDriverManager;
import mainPage.BlockQuestions;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;

// новое - второе окружение
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

// новое - импорты
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

@RunWith(Parameterized.class) // указываем, что тест параметризованный
public class FAQParameterizedTest {
    private WebDriver chromeDriver; // изменено для Google Chrome
    private WebDriver firefoxDriver; // новое - для Mozilla Firefox
    // параметры для теста
    private final int index; // индекс вопроса
    private final String expectedQuestion; // ожидаемый текст вопроса
    private final String expectedAnswer; // ожидаемый текст ответа

    // новое - список для хранения ошибок
    private final List<String> errors = new ArrayList<>();

    // конструктор для параметров
    public FAQParameterizedTest(int index, String expectedQuestion, String expectedAnswer) {
        this.index = index;
        this.expectedQuestion = expectedQuestion;
        this.expectedAnswer = expectedAnswer;
    }
    // метод, возвращающий данные для теста
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{ // взяты из требований к диплому по ручному тестированию
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватит на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {2, "Сможете привезти самокат прямо сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {3, "Хочу сразу несколько самокатов! Так можно?", "Пока что так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Если что-то срочное — всегда можно позвонить в поддержку по номеру 0101."},
                {5, "Можно ли отменить заказ?", "Да, отменить можно, пока курьер не выдвинулся к вам с самокатом. Штрафа не будет, объяснительной записки не попросим."},
                {6, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат в эту дату до конца дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 2030, суточная аренда закончится 9 мая в 2030."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }
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
    public void testFAQ() {
        // новое
        testInBrowser(chromeDriver, "Chrome");
        testInBrowser(firefoxDriver, "Firefox");
        // новое - вывод всех ошибок для браузеров после завершения теста
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.err.println(error);
            }
            Assert.fail("Обнаружены ошибки в тесте.");
        }
    }
    private void testInBrowser(WebDriver driver, String browserName) {
        try { // новое

        // открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru");
        // создаем объект класса
        BlockQuestions blockQuestions = new BlockQuestions();

        /* прокручиваем до блока вопросов
        blockQuestions.scrollToBlockQuestions(driver); - удалено */

        // прокручиваем до вопроса
        blockQuestions.scrollToQuestion(driver, index);
        // кликаем на вопрос
        blockQuestions.clickQuestion(driver, index);
        // изменено - сравниваем текст вопроса
            String actualQuestion = blockQuestions.getQuestionText(driver, index);
            if (!expectedQuestion.equals(actualQuestion)) {
                errors.add(String.format("Ошибка в браузере %s: Текст вопроса не совпадает. Ожидаемый: '%s'. Фактический: '%s'.",
                        browserName, expectedQuestion, actualQuestion));
            }
        // изменено - сравниваем текст ответа
            String actualAnswer = blockQuestions.getAnswerText(driver, index);
            if (!expectedAnswer.equals(actualAnswer)) {
                errors.add(String.format("Ошибка в браузере %s: Текст ответа не совпадает. Ожидаемый: '%s'. Фактический: '%s'.",
                        browserName, expectedAnswer, actualAnswer));
            }
        // новое - проверяем все утверждения и выводим ошибки, если они есть
        } catch (Exception e) {
            errors.add("Ошибка в браузере " + browserName + ": " + e.getMessage());
        }
    }
    @After
    public void tearDown() {
        // закрываем браузер после теста
        if (chromeDriver != null) { // изменено
            chromeDriver.quit();
        }
        if (firefoxDriver != null) { // новое
            firefoxDriver.quit();
        }
    }
}