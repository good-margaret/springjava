package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import tech.reliab.course.tishchenkomv.bank.springjava.enums.BankOfficeStatusEnum;

import java.util.List;
import java.util.Random;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offices")
public class BankOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="office_name", length=50, nullable=false, unique=false)
    private String name;

    @Column(name = "address", length=50, nullable=false, unique=false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = false)
    private BankOfficeStatusEnum status;

    @ManyToOne
    private Bank bank;

    @Column(name = "can_place_atm", nullable=false, unique=false)
    private boolean canPlaceAtm;

    @OneToMany(mappedBy = "address")
    private List<BankAtm> atms;

    @OneToMany(mappedBy = "bankOffice")
    private List<Employee> employees;

    @Column(name = "can_issue_loans", nullable=false, unique=false)
    private boolean canIssueLoans;

    @Column(name = "can_dispense_cash", nullable=false, unique=false)
    private boolean canDispenseCash;

    @Column(name = "can_accept_deposits", nullable=false, unique=false)
    private boolean canAcceptDeposits;

    @Column(name = "cash_amount", nullable=false, unique=false)
    private double cashAmount;

    @Column(name = "rent_cost", nullable=false, unique=false)
    private double rentCost;

    public BankOffice(String name, String address, Bank bank, boolean canPlaceAtm,
                      boolean canIssueLoans, boolean canDispenseCash, boolean canAcceptDeposits) {
        this.name = name;
        this.address = address;
        this.status = BankOfficeStatusEnum.randomStatus();
        this.bank = bank;
        this.canPlaceAtm = true;
        this.canIssueLoans = true;
        this.canDispenseCash = true;
        this.canAcceptDeposits = true;
//        this.cashAmount = generateCashAmount();
//        this.rentCost = generateRentCost();
    }


    private String toRusBoolean(boolean expression) {
        return expression ? "Да" : "Нет";
    }

    private String toRusAtmStatus (BankOfficeStatusEnum status) {
        if (status.equals(BankOfficeStatusEnum.WORKING))
            return "Работает";
        else
            return "Не работает";
    }

    @Override
    public String toString() {
        return "\nОфис \n" +
                "Название: " + name + '\n' +
                "Адрес: " + address + '\n' +
                "Статус: " + toRusAtmStatus(status) + '\n' +
                "Можно ли разместить банкомат: " + toRusBoolean(canPlaceAtm) + '\n' +
                "Число банкоматов: " + atms.size() + '\n' +
                "Возможность оформления кредита: " + toRusBoolean(canIssueLoans) + '\n' +
                "Возможность выдачи наличных: " + toRusBoolean(canDispenseCash) + '\n' +
                "Возможность внести деньги: " + toRusBoolean(canAcceptDeposits) + '\n' +
                "Количество денег в офисе: " + String.format("%.2f",cashAmount) + '\n' +
                "Стоимость аренды: " + String.format("%.2f", rentCost);
    }
}
