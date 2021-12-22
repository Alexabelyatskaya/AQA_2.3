package ru.netology.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationByCardInfo {
    private final String city;
    private final LocalDate cardExpire;
    private final String name;
    private final String phone;
}
