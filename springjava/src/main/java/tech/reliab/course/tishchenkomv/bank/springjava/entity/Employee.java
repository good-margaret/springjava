package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name", nullable = false, unique = false)
    private String fullName;

    @Column(name = "birth_date", nullable = false, unique = false)
    private LocalDate birthDate;

    @Column(name = "position", nullable = false, unique = false)
    private String position;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(name = "works_remotely", nullable = false, unique = false)
    private boolean worksRemotely;

    @ManyToOne
    @JoinColumn(name = "bank_office_id", nullable = false)
    private BankOffice bankOffice;

    @OneToMany(mappedBy = "servicingEmployee")
    List<BankAtm> bankAtms;

    @Column(name = "can_issue_credits", nullable = false, unique = false)
    private boolean canIssueCredits;

    @Column(name = "salary", nullable = false, unique = false)
    private double salary;

    public Employee(String fullName, LocalDate birthDate, String position,
                    boolean worksRemotely, boolean canIssueCredits,
                    Bank bank, BankOffice bankOffice, double salary) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.position = position;
        this.bank = bank;
        this.bankOffice = bankOffice;
        this.salary = salary;
        this.canIssueCredits = canIssueCredits;
        this.worksRemotely = worksRemotely;
    }

    @Override
    public String toString() {
        return "\nСотрудник\n" +
                "ФИО: " + fullName + '\n' +
                "Дата раждения" + birthDate + '\n' +
                "Должность" + position + '\n' +
                "Название банка: " + (bank != null ? bank.getName() : "Не указано") + '\n' +
                "Работает ли удаленно: " + worksRemotely + '\n' +
                "Банковский офис: " + (bankOffice != null ? bankOffice.getName() : "Не указано") + '\n' +
                "Дозволение выдавать кредиты: " + canIssueCredits +'\n' +
                "Зарплата: " + String.format("%.2f", salary);
    }
}
