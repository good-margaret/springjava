package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import tech.reliab.course.tishchenkomv.bank.springjava.enums.BankAtmStatusEnum;

import java.util.Random;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_atms")
public class BankAtm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="atm_name", length=50, nullable=false, unique=false)
    private String name;

    @Column(name = "address", length=50, nullable=false, unique=false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = false)
    private BankAtmStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private BankOffice office;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee servicingEmployee;

    @Column(name = "can_dispense_cash", nullable = false)
    private boolean canDispenseCash;

    @Column(name = "can_accept_deposits", nullable = false)
    private boolean canAcceptDeposits;

    @Column(name = "cash_amount", nullable = false)
    private double cashAmount;

    @Column(name = "maintenance_cost", nullable = false)
    private double maintenanceCost;

    public BankAtm(String name, String address, Bank bank, BankOffice office, Employee employee,
                   boolean canDispenseCash, boolean canAcceptDeposits) {
         this.name = name;
         this.address = address;
         this.bank = bank;
         this.office = office;
         this.servicingEmployee = employee;
         this.canDispenseCash = canDispenseCash;
         this.canAcceptDeposits = canAcceptDeposits;
//         this.status = BankAtmStatusEnum.randomStatus();
    }

    private String toRusBoolean(boolean expression) {
        return expression ? "Да" : "Нет";
    }

    @Override
    public String toString() {
        return "\nБанкомат\n" +
                "Название: " + name + '\n' +
                "Статус: " + status + '\n' +
                "Название банка: " + (bank != null ? bank.getName() : "Не указано") + '\n' +
                "Расположен в офисе: " + (office != null ? office.getName() : "Не указано") + '\n' +
                "Обслуживающий работник: " + (servicingEmployee != null ? servicingEmployee.getFullName() : "Не указано") + '\n' +
                "Возможность выдачи наличных: " + toRusBoolean(canDispenseCash) + '\n' +
                "Возможность ввести деньги: " + toRusBoolean(canAcceptDeposits) + '\n' +
                "Количество наличных денег: " + String.format("%.2f", cashAmount) + '\n' +
                "Стоимость обслуживания банкомата: " + String.format("%.2f", maintenanceCost);
    }
}
