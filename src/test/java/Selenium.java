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

public class Selenium {
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
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldTestCorrect(){
        List <WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Анна");
        textFields.get(1).sendKeys("+79305698778");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldTestIncorrectEnterName(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Maik");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79305698778");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldTestIncorrectEnterNameDefis(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Аннф_Петровна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79305698778");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldTestIncorrectEnterTelephone(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Маша");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+793056987784");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldCreateNullName(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+793056987784");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldCreateNullTelephone(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Анна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actualText);
    }

    @Test
    public void shouldNotChekbox(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Анна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79305698778");
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        assertEquals(expected, actualText);
    }
}