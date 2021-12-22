package ru.netology.domain;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

        @UtilityClass
        public static class Registration {

            public RegistrationByCardInfo generateByCard(String locale) {
                Faker faker = new Faker(new Locale(locale));
                return new RegistrationByCardInfo(
                        faker.address().city(),
                        LocalDate.now().plusYears(1),
                        faker.name().fullName(),
                        faker.phoneNumber().phoneNumber()
                );
            }
        }
}
