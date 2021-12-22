package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.domain.DataGenerator;
import ru.netology.domain.RegistrationByCardInfo;

import static com.codeborne.selenide.Selenide.*;

public class CardTest {

    @Test
    void shouldRegisterByAccountNumber() {
        open("http://localhost:9999");

        RegistrationByCardInfo info = DataGenerator.Registration.generateByCard("ru");

        System.out.println(info);
    }
}
