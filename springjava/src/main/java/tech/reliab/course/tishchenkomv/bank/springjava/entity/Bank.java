package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import java.util.Random;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name="bank_name", length=50, nullable=false, unique=true)
    private String name;

    @Column(name = "rating", nullable = false, unique=false)
    private int rating;

    @Column(name = "total_cash", nullable=false, unique=false)
    private double totalCash;

    @Column(name = "interest_rate", nullable=false, unique=false)
    private double interestRate;

    @OneToMany(mappedBy = "bank")
    private List<BankOffice> offices;

    @OneToMany(mappedBy = "bank")
    private List<BankAtm> atms;

    @OneToMany(mappedBy = "bank")
    private List<Employee> employees;

    @OneToMany(mappedBy = "bank")
    private List<CreditAccount> creditAccounts;

    @OneToMany(mappedBy = "bank")
    private List<PaymentAccount> paymentAccounts;

    @ManyToMany(mappedBy = "bank")
    private List<User> users;

    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "\nБанк \n" +
                "Название банка: " + name +
                "\nКоличество офисов: " + offices.size() +
                "\nКоличество банкоматов: " + atms.size() +
                "\nКоличество сотрудников: " + employees.size() +
                "\nКоличество клиентов: " +  users.size() +
                "\nРейтинг: " + rating +
                "\nДенежная сумма: " + String.format("%.2f", totalCash) +
                "\nПроцентная ставка: " + String.format("%.2f",interestRate);
    }
}
