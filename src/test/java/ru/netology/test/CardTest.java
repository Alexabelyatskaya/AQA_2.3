package ru.netology.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.domain.DataGenerator;
import ru.netology.domain.RegistrationByCardInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardTest {

    @Test
    void shouldRegisterCard() {
        open("http://localhost:9999");

        RegistrationByCardInfo firstAttempt = DataGenerator.Registration.generateByCard("ru");

        String firstDate = generateDate(27);
        String secondDate = generateDate(35);


        // firstAttempt

        $("[placeholder='Город']").setValue(firstAttempt.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[name='name']").setValue(firstAttempt.getName());
        $("[name='phone']").setValue(firstAttempt.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));


        // secondAttempt

        $("[placeholder='Город']").sendKeys(Keys.CONTROL + "a");
        $("[placeholder='Город']").sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(BACK_SPACE);
        $("[name='name']").sendKeys(Keys.CONTROL + "a");
        $("[name='name']").sendKeys(Keys.DELETE);
        $("[name='phone']").sendKeys(Keys.CONTROL + "a");
        $("[name='phone']").sendKeys(Keys.DELETE);
        $("[data-test-id='agreement']").click();

        $("[placeholder='Город']").setValue(firstAttempt.getCity());
        $("[data-test-id='date'] input").setValue(secondDate);
        $("[name='name']").setValue(firstAttempt.getName());
        $("[name='phone']").setValue(firstAttempt.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $$(".button__text").find(exactText("Перепланировать")).click();

        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(15));
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
