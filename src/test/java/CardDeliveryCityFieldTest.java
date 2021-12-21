import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryCityFieldTest {
    String calculationDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = calculationDate();

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendFormWithNonexistentCity() {
        $("[data-test-id='city'] input").setValue("Изумрудный");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldSendFormWithForeignCity() {
        $("[data-test-id='city'] input").setValue("Нью-Йорк");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldSendFormWithCityInQwerty() {
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldSendFormWithSpecialSymbolOfCityField() {
        $("[data-test-id='city'] input").setValue("Москва!");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldSendFormWithEmptyCityField() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
}