package tech.reliab.course.tishchenkomv.bank.springjava.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountRequest {
    private int user_id;
    private int bank_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int creditMonths;
    private double creditAmount;
    private double monthlyPayment;
    private double interestRate;
    private int employee_id;
    private int paymentAccount_id;
}
