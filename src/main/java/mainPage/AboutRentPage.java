package mainPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AboutRentPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // конструктор
    public AboutRentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);// явное ожидание
    }
    // локаторы
    // новое - локатор заголовка формы
    private final By orderHeaderAboutRent = By.className("Order_Header__BZXOb");
    // поле Когда привезти самокат
    public By dateFieldInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    public By calendar = By.className("react-datepicker");
    // поле Срок аренды
    public By rentalPeriodField = By.xpath("//div[contains(@class, 'Dropdown-root')]");
    // изменено
    public By rentalPeriodDropdownOptions = By.xpath("//div[contains(@class, 'Dropdown-option')]");
    // поле Цвет
    public By colourField = By.className("Order_Checkboxes__3lWSI");
    // поле Комментарий
    public By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // изменено - кнопка Заказать внизу формы
    public By orderButtonBottom = By.xpath(".//button[text()='Назад']/parent::div/button[text()='Заказать']");
    // изменено - всплывающее окно Хотите оформить заказ
    public By orderConfirmationPopup = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Хотите оформить заказ?')]");
    // изменено - кнопка Да
    public By orderConfirmationYesButton = By.xpath("//button[text()='Да']");
    // изменено - всплывающее окно Заказ оформлен
    public By orderSuccessPopup = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Заказ оформлен')]");

    // методы
    // проверка отображения заголовка формы
    public boolean isOrderHeaderDisplayed() {
        return driver.findElement(orderHeaderAboutRent).isDisplayed();
    }
    // выбрать дату в поле Когда привезти самокат
    public void selectDate(String date) {
        // парсим дату на составляющие
        String[] dateParts = date.split("\\.");
        String dayOfMonth = dateParts[0]; // день месяца
        String monthNumber = dateParts[1]; // номер месяца
        String fullYear = dateParts[2]; // год (полный формат)
        // локатор для дня
        String calendarDayLocator = String.format(
                "//div[contains(@class, 'react-datepicker__day') " +
                        "and not(contains(@class, 'react-datepicker__day--outside-month')) " +
                        "and text()='%s']",
                dayOfMonth
        );
        driver.findElement(dateFieldInput).click(); // кликаем по полю
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendar));
        WebElement dayElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(calendarDayLocator))
        );
        dayElement.click(); // кликаем по выбранному дню
    }

    // выбрать Срок аренды
    public void selectRentalPeriod(String period) {
        // шаг 1: кликнуть по полю Срок аренды, чтобы открыть выпадающее меню
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        dropdown.click();
        // шаг 2: ожидание появления выпадающего меню
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodDropdownOptions))
                .findElements(rentalPeriodDropdownOptions)
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
    // изменено - заполнить поле Комментарий или не заполнять
    public void writeComment(String comment) {
        WebElement commentFieldElement = driver.findElement(commentField);
        commentFieldElement.clear(); // очистка поля
        commentFieldElement.sendKeys(comment);
    }
    // клик по кнопке Заказать
    public void clickOrderButtonBottom() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        button.click();
    }
    // проверка окна согласия и клик по кнопке Да
    public void giveConsent() {
        try {
            // Проверяем, что окно подтверждения заказа появилось
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationPopup));
            // Проверяем, что кнопка "Да" кликабельна
            WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(orderConfirmationYesButton));
            yesButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при подтверждении заказа: " + e.getMessage());
        }
    }
    // проверка окна Заказ оформлен
    public void orderHasBeenPlaced() {
        try {
            WebElement orderSuccessPopupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessPopup));
        } catch (Exception e) {
            throw new RuntimeException("Окно 'Заказ оформлен' не появилось: " + e.getMessage());
        }
    }
}