import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryPhoneFieldTest {

    String calculationDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = calculationDate();

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendFormWithTenNumbersOfPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+7915762711");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendFormWithTwelveNumbersOfPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+791576271122");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendFormWithSpecialSymbolOfPhoneField() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+7915762711!");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendFormWhenPhoneBeginningOnEightNumber() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("891576271122");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendFormWithCyrillicAlphabetOfPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+7915762711ф");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendFormWithLatinAlphabetOfPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+7915762711m");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldSendFormWithEmptyPhoneField() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
}
