package tech.reliab.course.tishchenkomv.bank.springjava.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_accounts")
public class PaymentAccount {

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

    @Column(name = "balance", nullable = false)
    private double balance;

    public PaymentAccount(User user, Bank bank) {
        this.user = user;
        this.bank = bank;
        this.balance = 0;
    }

    @Override
    public String toString() {
        return "\nПлатежный счет" +
                "\nid: " + id +
                "\nКлиент: " + user +
                "\nНазвание банка" + bank +
                "\nБаланс счета: " + balance;
    }
}
