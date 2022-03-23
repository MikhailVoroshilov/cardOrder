import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationCard {
    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
//        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldTestCorrect(){
        driver.get("http://localhost:9999/");
        List <WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Анна");
        textFields.get(1).sendKeys("+79305698778");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.className("paragraph")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldTestIncorrectEnterName(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Maik");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79305698778");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.className("input__sub")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldTestIncorrectEnterNameDefis(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Аннф_Петровна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79305698778");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.className("input__sub")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldTestIncorrectEnterTelephone(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Маша");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+793056987784");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        List <WebElement> textErrors = driver.findElements(By.className("input__sub"));
        String actualText = textErrors.get(1).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldCreateNullName(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+793056987784");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        List <WebElement> textErrors = driver.findElements(By.className("input__sub"));
        String actualText = textErrors.get(0).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldCreateNullTelephone(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Анна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        List <WebElement> textErrors = driver.findElements(By.className("input__sub"));
        String actualText = textErrors.get(1).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actualText);
    }
}
