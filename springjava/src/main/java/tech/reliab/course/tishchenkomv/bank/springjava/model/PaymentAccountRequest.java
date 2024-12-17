package tech.reliab.course.tishchenkomv.bank.springjava.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAccountRequest {
    private int bank_id;
    private int user_id;
    private double balance;

}
