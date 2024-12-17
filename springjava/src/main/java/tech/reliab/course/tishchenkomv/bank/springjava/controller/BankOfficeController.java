package tech.reliab.course.tishchenkomv.bank.springjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankOffice;
import tech.reliab.course.tishchenkomv.bank.springjava.model.BankOfficeRequest;

import java.util.List;

public interface BankOfficeController {

    ResponseEntity<BankOffice> createBankOffice(@RequestBody BankOfficeRequest bankOfficeRequest);

    ResponseEntity<Void> deleteBankOffice(@PathVariable int id);

    ResponseEntity<BankOffice> updateBankOffice(@PathVariable int id, @RequestParam(name = "name") String name);

    ResponseEntity<BankOffice> getBankOfficeById(@PathVariable int id);

    ResponseEntity<List<BankOffice>> getAllBankOffices();
}