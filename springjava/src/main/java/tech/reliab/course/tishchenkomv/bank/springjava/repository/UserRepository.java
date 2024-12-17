package tech.reliab.course.tishchenkomv.bank.springjava.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

    void deleteById(int id);

    List<User> findAllByBankId(int bankId);
}
