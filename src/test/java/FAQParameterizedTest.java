import io.github.bonigarcia.wdm.WebDriverManager;
import mainPage.BlockQuestions;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class) // указываем, что тест параметризованный
public class FAQParameterizedTest {
    private WebDriver driver;
    // параметры для теста
    private final int index; // индекс вопроса
    private final String expectedQuestion; // ожидаемый текст вопроса
    private final String expectedAnswer; // ожидаемый текст ответа
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
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test
    public void testFAQ() {
        // открываем страницу
        driver.get("https://qa-scooter.praktikum-services.ru");
        // создаем объект класса
        BlockQuestions blockQuestions = new BlockQuestions();
        // прокручиваем до блока вопросов
        blockQuestions.scrollToBlockQuestions(driver);
        // кликаем на вопрос
        blockQuestions.clickQuestion(driver, index);
        // сравниваем текст вопроса
        String actualQuestion = blockQuestions.getQuestionText(driver, index);
        assertEquals("Текст вопроса не совпадает", expectedQuestion, actualQuestion);
        // сравниваем текст ответа
        String actualAnswer = blockQuestions.getAnswerText(driver, index);
        assertEquals("Текст ответа не совпадает", expectedAnswer, actualAnswer);
    }
    @After
    public void tearDown() {
        // закрываем браузер после теста
        if (driver != null) {
            driver.quit();
        }
    }
}
