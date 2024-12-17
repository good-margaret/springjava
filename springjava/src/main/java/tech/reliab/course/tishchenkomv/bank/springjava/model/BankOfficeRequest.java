package tech.reliab.course.tishchenkomv.bank.springjava.model;

import lombok.*;
import tech.reliab.course.tishchenkomv.bank.springjava.enums.BankOfficeStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankOfficeRequest {

    private String name;
    private String address;
    private boolean canPlaceAtm;
    private double rentCost;
    private int bank_id;
    private BankOfficeStatusEnum status;
    private boolean canIssueLoans;
    private boolean canDispenseCash;
    private boolean canAcceptDeposits;
    private double cashAmount;
}
