package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_accounts")
public class CreditAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(name = "start_date", nullable = false, unique = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false, unique = false)
    private LocalDate endDate;

    @Column(name = "credit_months", nullable=false, unique=false)
    private int creditMonths;

    @Column(name = "credit_amount", nullable=false, unique=false)
    private double creditAmount;

    @Column(name = "monthly_payment", nullable=false, unique=false)
    private double monthlyPayment;

    @Column(name = "interest_rate", nullable=false, unique=false)
    private double interestRate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "payment_account_id")
    private PaymentAccount paymentAccount;

    public CreditAccount(User user, Bank bankName, LocalDate startDate, int creditMonths,
                         double creditAmount, double interestRate, Employee employee, PaymentAccount paymentAccount) {
        this.user = user;
        this.bank = bankName;
        this.startDate = startDate;
        this.creditMonths = creditMonths;
        this.creditAmount = creditAmount;
        this.interestRate = interestRate;
        this.employee = employee;
        this.paymentAccount = paymentAccount;
    }

    private LocalDate calculateEndDate(LocalDate startDate, int months) {
        return startDate.plusMonths(months);
    }

    private double calculateMonthlyPayment(double creditAmount, double interestRate, int months) {
        double monthlyRate = interestRate / 100 / 12;
        return (creditAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate, creditMonths);
    }

    public void setCreditMonths(int creditMonths) {
        this.creditMonths = creditMonths;
        this.endDate = calculateEndDate(startDate, creditMonths);
        this.monthlyPayment = calculateMonthlyPayment(creditAmount, interestRate, creditMonths);
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
        this.monthlyPayment = calculateMonthlyPayment(creditAmount, interestRate, creditMonths);
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
        this.monthlyPayment = calculateMonthlyPayment(creditAmount, interestRate, creditMonths);
    }

    @Override
    public String toString() {
        return "\n Кредитный счет\n" +
                "Пользователь: " + (user != null ? user.getFullName() : "Не указано") + '\n' +
                "Банк: " + (bank != null ? bank.getName() : "Не указано") + '\n' +
                "Дата начала кредита: " + startDate + '\n' +
                "Дата окончания кредита" + endDate +'\n' +
                "Длительность кредита (месяца): " + creditMonths +'\n' +
                "Сумма кредита: " + String.format("%.2f", creditAmount) +'\n' +
                "Ежемесячная выплата: " + String.format("%.2f", monthlyPayment) + '\n' +
                "Процентная ставка: " + String.format("%.2f", interestRate) + '\n' +
                "Сотрудник: " + (employee != null ? employee.getFullName() : "Не указано") + '\n' +
                "Платежный счет" + (paymentAccount != null ? paymentAccount.getId() : "Не указано");

    }
}
