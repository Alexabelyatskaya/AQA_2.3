package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationByCardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardTest {

    @Test
    void shouldRegisterCard() {
        open("http://localhost:9999");

        RegistrationByCardInfo firstAttempt = DataGenerator.Registration.generateByCard("ru");

        String firstDate = DataGenerator.generateDate(27);
        String secondDate = DataGenerator.generateDate(35);


        // firstAttempt

        $("[placeholder='Город']").setValue(firstAttempt.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[name='name']").setValue(firstAttempt.getName());
        $("[name='phone']").setValue(firstAttempt.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $(".notification__content")
                .shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));


        // secondAttempt

        $("[placeholder='Дата встречи']").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondDate);
        $$(".button__text").find(exactText("Запланировать")).click();

        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $$(".button__text").find(exactText("Перепланировать")).click();

        $(".notification__content")
                .shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + secondDate), Duration.ofSeconds(15));
    }
}
