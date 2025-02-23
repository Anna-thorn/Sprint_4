package mainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

public class BlockQuestions {
    // локаторы
    // блок вопросов
    public By questions = By.className("Home_FAQ__3uVm4");
    // вопросы 1-8
    public By question0 = By.id("accordion__heading-0");
    public By question1 = By.id("accordion__heading-1");
    public By question2 = By.id("accordion__heading-2");
    public By question3 = By.id("accordion__heading-3");
    public By question4 = By.id("accordion__heading-4");
    public By question5 = By.id("accordion__heading-5");
    public By question6 = By.id("accordion__heading-6");
    public By question7 = By.id("accordion__heading-7");
    // ответы 1-8
    public By answer0 = By.id("accordion__panel-0");
    public By answer1 = By.id("accordion__panel-1");
    public By answer2 = By.id("accordion__panel-2");
    public By answer3 = By.id("accordion__panel-3");
    public By answer4 = By.id("accordion__panel-4");
    public By answer5 = By.id("accordion__panel-5");
    public By answer6 = By.id("accordion__panel-6");
    public By answer7 = By.id("accordion__panel-7");

    // методы
    // прокручивание до блока с вопросами
    public void scrollToBlockQuestions(WebDriver driver) {
        WebElement element = driver.findElement(questions);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
    // клик на вопрос
    public void clickQuestion(WebDriver driver, int index) {
        By questionLocator = getQuestionLocator(index);
        WebElement questionElement = driver.findElement(questionLocator);
        questionElement.click();
    }
    // получение текста вопроса
    public String getQuestionText(WebDriver driver, int index) {
        By questionLocator = getQuestionLocator(index);
        WebElement questionElement = driver.findElement(questionLocator);
        return questionElement.getText();
    }
    // получение текста ответа
    public String getAnswerText(WebDriver driver, int index) {
        By answerLocator = getAnswerLocator(index);
        WebElement answerElement = driver.findElement(answerLocator);
        return answerElement.getText();
    }
    // вспомогательный метод для получения локатора вопроса по индексу
    private By getQuestionLocator(int index) {
        switch (index) {
            case 0: return question0;
            case 1: return question1;
            case 2: return question2;
            case 3: return question3;
            case 4: return question4;
            case 5: return question5;
            case 6: return question6;
            case 7: return question7;
            default: throw new IllegalArgumentException("Неверный индекс вопроса: " + index);
        }
    }
    // вспомогательный метод для получения локатора ответа по индексу
    private By getAnswerLocator(int index) {
        switch (index) {
            case 0: return answer0;
            case 1: return answer1;
            case 2: return answer2;
            case 3: return answer3;
            case 4: return answer4;
            case 5: return answer5;
            case 6: return answer6;
            case 7: return answer7;
            default: throw new IllegalArgumentException("Неверный индекс ответа: " + index);
        }
    }
}