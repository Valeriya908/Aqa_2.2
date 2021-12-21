import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryDateFieldTest {

    String calculationDateInFuture() {
        return LocalDate.now().plusDays(365).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String calculationDateInPast() {
        return LocalDate.now().minusDays(365).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String calculationCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String futureDate = calculationDateInFuture();
    String lastDate = calculationDateInPast();
    String currentDate = calculationCurrentDate();

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendFormWithFutureDate() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + futureDate));
    }

    @Test
    public void shouldSendFormWithLastDate() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(lastDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendFormWithCurrentDate() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendFormWithNonexistentDate() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("30.02.2022");
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void shouldSendFormWithEmptyDateField() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }
}
