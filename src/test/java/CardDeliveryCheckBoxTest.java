import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryCheckBoxTest {
    String calculationDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = calculationDate();

    @BeforeEach
    public void setUpTest() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSendFormWhenCheckboxNotClick() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Анна Попова");
        $("[data-test-id='phone'] input").setValue("+79157627112");
        $("[class='button__content']").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    public void shouldSendEmptyFormWhenCheckboxClick() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[lang='en']").click();
        $("[data-test-id='agreement'] .checkbox__box ").click();
        $("[class='button__content']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }
}
