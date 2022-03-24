import com.codeborne.selenide.ElementsCollection;
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

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
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
        open("http://localhost:9999/");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldTestCorrect() {

        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79305698778");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldTestIncorrectEnterName() {
        $("[data-test-id='name'] input").setValue("Maik");
        $("[data-test-id='phone'] input").setValue("+79305698778");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();

        $(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestIncorrectEnterNameDefis() {
        $("[data-test-id='name'] input").setValue("Аннф_Петровна");
        $("[data-test-id='phone'] input").setValue("+79305698778");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();

        $(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestIncorrectEnterTelephone() {
        $("[data-test-id='name'] input").setValue("Маша");
        $("[data-test-id='phone'] input").setValue("+793056987784");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();

        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldCreateNullName() {
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+793056987784");
        $("[data-test-id=agreement]").click();
        $(".button").click();

        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldCreateNullTelephone() {
        $("[data-test-id='name'] input").setValue("Анна");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(By.tagName("button")).click();

        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
