package tech.reliab.course.tishchenkomv.bank.springjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankOffice;

import java.util.List;
import java.util.Optional;

public interface BankOfficeRepository extends JpaRepository<BankOffice, Integer> {

    Optional<BankOffice> findById(int id);

    void deleteById(int id);

    List<BankOffice> findAllByBankId(int bankId);
}
