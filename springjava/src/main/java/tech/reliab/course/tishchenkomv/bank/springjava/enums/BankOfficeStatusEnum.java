package tech.reliab.course.tishchenkomv.bank.springjava.enums;

import java.util.Random;

public enum BankOfficeStatusEnum {

    WORKING,
    NOT_WORKING;

    private static final Random RANDOM = new Random();

    public static BankOfficeStatusEnum randomStatus()  {
        BankOfficeStatusEnum[] statuses = values();
        return statuses[RANDOM.nextInt(statuses.length)];
    }
}
