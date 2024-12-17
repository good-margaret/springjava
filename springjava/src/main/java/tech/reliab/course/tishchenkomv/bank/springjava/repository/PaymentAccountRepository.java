package tech.reliab.course.tishchenkomv.bank.springjava.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.PaymentAccount;

import java.util.List;
import java.util.Optional;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {

    Optional<PaymentAccount> findById(int id);

    void deleteById(int id);

    Optional<PaymentAccount> findAllByUserId(int userId);
}
