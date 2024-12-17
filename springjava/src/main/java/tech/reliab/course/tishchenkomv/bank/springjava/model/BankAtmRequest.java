package tech.reliab.course.tishchenkomv.bank.springjava.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAtmRequest {
    private String name;
    private String address;
    private int bank_id;
    private int location_id;
    private int employee_id;
    private boolean canDispenseCash;
    private boolean canAcceptDeposits;
    private double cashAmount;
    private double maintenanceCost;
}
