package tech.reliab.course.tishchenkomv.bank.springjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;

import java.util.List;

public interface BankController {

    ResponseEntity<Bank> createBank(@RequestParam(name = "bankName") String bankName);

    ResponseEntity<Void> deleteBank(@PathVariable int id);

    ResponseEntity<Bank> updateBank(@PathVariable int id, @RequestParam(name = "bankName") String bankName);

    ResponseEntity<Bank> getBankById(@PathVariable int id);

    ResponseEntity<List<Bank>> getAllBanks();
}
