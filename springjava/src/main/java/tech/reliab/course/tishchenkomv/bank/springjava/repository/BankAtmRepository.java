package tech.reliab.course.tishchenkomv.bank.springjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankAtm;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAtmRepository extends JpaRepository<BankAtm, Integer> {

    Optional<BankAtm> findById(int id);

    void deleteById(int id);

    List<BankAtm> findAllByBankId(int id);
}