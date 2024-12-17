package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "monthly_income", nullable = false)
    private double monthlyIncome;

    @Column(name = "credit_rating", nullable = false)
    private int creditRating;

    @ManyToMany
    @JoinTable(
            name = "user_banks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private List<Bank> banks;

    @OneToMany(mappedBy = "user")
    private List<CreditAccount> creditAccounts;

    @OneToMany(mappedBy = "user")
    private List<PaymentAccount> paymentAccounts;

    public User(String fullName, LocalDate birthDate, String job) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.job = job;
        this.monthlyIncome = generateRandomIncome();
        this.creditRating = calculateCreditRating();
    }

    private double generateRandomIncome() {
        Random random = new Random();
        return 1000 + random.nextInt(9001); // От 1000 до 10000
    }

    private int calculateCreditRating() {
        return (int) (monthlyIncome / 1000) * 100;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
        this.creditRating = calculateCreditRating();
    }

    @Override
    public String toString() {
        return "\nКлиент\n" +
                "ФИО: " + fullName + '\'' +
                "\nДата рождения: " + birthDate +
                "\nПрофессия: " + job + '\'' +
                "\nДоход: " + String.format("%.2f", monthlyIncome) +
                "\nСписок банков: " + banks.stream().map(Bank::getName).toList() + '\'' +
                "\nКредитный счета: " + creditAccounts.size() +
                "\nПлатежные счета" + paymentAccounts.size() +
                "\nКредитный рейтинг" + creditRating;
    }
}
