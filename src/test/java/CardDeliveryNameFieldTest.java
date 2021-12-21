import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryNameFieldTest {

    String calculationDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = calculationDate();

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendFormWithDoubleSurname() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова-Мейхель");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    public void shouldSendFormWithNumbersInNameField() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("1234 Попова-Мейхель");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendFormWithNameFieldInQwerty() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Anna Popova");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendFormWithSpecialSymbolOfNameField() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова!");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendFormWithoutSurname() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planningDate));
    }
    // В теории возможно, что в паспорте может не быть фамилии и суд признает это законным.

    @Test
    public void shouldSendFormWithEmptyNameField() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
}
