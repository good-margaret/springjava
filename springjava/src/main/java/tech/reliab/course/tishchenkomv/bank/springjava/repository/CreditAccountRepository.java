package tech.reliab.course.tishchenkomv.bank.springjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.CreditAccount;

import java.util.List;
import java.util.Optional;

public interface CreditAccountRepository extends JpaRepository<CreditAccount, Integer> {

    Optional<CreditAccount> findById(int id);

    void deleteById(int id);

    Optional<CreditAccount> findByUserId(int userId);
}
