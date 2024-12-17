package tech.reliab.course.tishchenkomv.bank.springjava.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.tishchenkomv.bank.springjava.controller.BankAtmController;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankAtm;
import tech.reliab.course.tishchenkomv.bank.springjava.model.BankAtmRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankAtmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-atms")
public class BankAtmControllerImpl implements BankAtmController {

    private final BankAtmService bankAtmService;

    @Override
    @PostMapping
    public ResponseEntity<BankAtm> createBankAtm(BankAtmRequest bankAtmRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAtmService.createBankAtm(bankAtmRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAtm(int id) {
        bankAtmService.deleteBankAtm(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<BankAtm> updateBankAtm(int id, String name) {
        return ResponseEntity.ok(bankAtmService.updateBankAtm(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BankAtm> getBankAtmById(int id) {
        return ResponseEntity.ok(bankAtmService.getBankAtmDtoById(id));
    }

    @Override
    @GetMapping("/all-by-bank/{bankId}")
    public ResponseEntity<List<BankAtm>> getAllBankAtmByBankId(int bankId) {
        return ResponseEntity.ok(bankAtmService.getAllBankAtmsByBankId(bankId));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BankAtm>> getAllBankAtms() {
        return ResponseEntity.ok(bankAtmService.getAllBankAtms());
    }
}
