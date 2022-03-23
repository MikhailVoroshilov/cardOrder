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
    public void shouldTest(){
        driver.get("http://localhost:9999/");
//        List <WebElement> textFields = driver.findElements(By.className("input__control"));
//        textFields.get(0).sendKeys("Анна");
//        textFields.get(1).sendKeys("+79305698778");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Анна");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79305698778");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String actualText = driver.findElement(By.className("paragraph")).getText().trim(); //trim- обрезка пустых символов(пробелов) по обе стороны текста
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actualText);
    }

}
