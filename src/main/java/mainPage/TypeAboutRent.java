package mainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TypeAboutRent {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // конструктор
    public TypeAboutRent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);// явное ожидание
    }
    // локаторы
    // поле Когда привезти самокат
    public By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    public By calendar = By.className("react-datepicker");
    // поле Срок аренды
    public By rentalPeriodField = By.xpath("//div[contains(@class, 'Dropdown-root')]");
    public By dropdownOptions = By.xpath("//div[contains(@class, 'Dropdown-option')]");
    // поле Цвет
    public By colourField = By.className("Order_Checkboxes__3lWSI");
    // поле Комментарий
    public By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // кнопка Заказать внизу формы
    public By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM')]");
    // всплывающее окно Хотите оформить заказ
    public By popupWindow1 = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Хотите оформить заказ?')]");
    // кнопка Да
    public By yesButton = By.xpath("//button[text()='Да']");
    // всплывающее окно Заказ оформлен
    public By popupWindow2 = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Заказ оформлен')]");

    // методы
    // выбрать дату в поле Когда привезти самокат
    public void selectDate(String date) {
        // парсим дату
        String[] parts = date.split("\\.");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        // локатор для дня
        String dayLocator = String.format(
                "//div[contains(@class, 'react-datepicker__day') " +
                        "and not(contains(@class, 'react-datepicker__day--outside-month')) " +
                        "and text()='%s']",
                day
        );
        driver.findElement(dateField).click(); // кликаем по полю
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendar));
        WebElement dayElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(dayLocator))
        );
        dayElement.click(); // кликаем по выбранному дню

    }
    // выбрать Срок аренды
    public void selectRentalPeriod(String period) {
        // шаг 1: кликнуть по полю Срок аренды, чтобы открыть выпадающее меню
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        dropdown.click();
        // шаг 2: ожидание появления выпадающего меню
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOptions))
                .findElements(dropdownOptions)
                .stream()
                .filter(e -> e.getText().trim().equals(period))
                .findFirst()
                .orElseThrow(() -> new java.util.NoSuchElementException("Срок аренды не найден: " + period))
                .click();
    }
    // выбрать Цвет
    public void selectColor(String color) {
        driver.findElement(colourField);
        if (!color.equals("black") && !color.equals("grey")) { // проверка допустимых цветов
            throw new IllegalArgumentException("Недопустимый цвет: " + color);
        }
        By checkboxLocator = By.id(color); // создаем локатор для чекбокса по id
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(checkboxLocator));
        if (!checkbox.isSelected()) { // выбор чекбокса, если он не выбран
            checkbox.click();
        }
    }
    // заполнить поле Комментарий или не заполнять
    public void writeComment() {
        WebElement commentFieldElement = driver.findElement(commentField);
        commentFieldElement.clear(); // очистка поля
        commentFieldElement.sendKeys("Больно, мне больно");
    }
    // клик по кнопке Заказать
    public void clickOrderButtonBottom() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        button.click();
    }
    // проверка окна согласия и клик по кнопке Да
    public void giveConsent() {
        driver.findElement(popupWindow1);
        driver.findElement(yesButton).click();
    }
    // проверка окна Заказ оформлен
    public void orderHasBeenPlaced() {
        WebElement popupWindow2Element = wait.until(ExpectedConditions.visibilityOfElementLocated(popupWindow2));
    }
}