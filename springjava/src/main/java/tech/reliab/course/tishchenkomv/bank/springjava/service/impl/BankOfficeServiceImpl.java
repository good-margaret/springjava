package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankOffice;
import tech.reliab.course.tishchenkomv.bank.springjava.model.BankOfficeRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.BankOfficeRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankOfficeService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankOfficeServiceImpl implements BankOfficeService {

    private final BankOfficeRepository bankOfficeRepository;
    private final BankService bankService;

    public BankOffice createBankOffice(BankOfficeRequest bankOfficeRequest) {
        Bank bank = bankService.getBankById(bankOfficeRequest.getBank_id());
        BankOffice bankOffice = new BankOffice(bankOfficeRequest.getName(), bankOfficeRequest.getAddress(), bank,
                bankOfficeRequest.isCanPlaceAtm(), bankOfficeRequest.isCanIssueLoans(),
                bankOfficeRequest.isCanDispenseCash(), bankOfficeRequest.isCanAcceptDeposits());
        bankOffice.setRentCost(generateRentCost());
        bankOffice.setCashAmount(generateOfficeMoney(bank));
        return bankOfficeRepository.save(bankOffice);
    }

    private double generateOfficeMoney(Bank bank) {
        return new Random().nextDouble(bank.getTotalCash());
    }

    private double generateRentCost() {
        Random rand = new Random();
        return rand.nextDouble() * 1000;
    }

    public BankOffice getBankOfficeById(int id) {
        return bankOfficeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BankOffice was not found"));
    }

    public BankOffice getBankDtoOfficeById(int id) {
        return getBankOfficeById(id);
    }

    public List<BankOffice> getAllBankOffices() {
        return bankOfficeRepository.findAll();
    }

    public BankOffice updateBankOffice(int id, String name) {
        BankOffice bankOffice = getBankOfficeById(id);
        bankOffice.setName(name);
        return bankOfficeRepository.save(bankOffice);
    }

    public void deleteBankAtm(int id) {
        bankOfficeRepository.deleteById(id);
    }
}
