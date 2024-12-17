package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankAtm;
import tech.reliab.course.tishchenkomv.bank.springjava.enums.BankAtmStatusEnum;
import tech.reliab.course.tishchenkomv.bank.springjava.model.BankAtmRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.BankAtmRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankAtmService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankOfficeService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class BankAtmServiceImpl implements BankAtmService {

    private final BankAtmRepository bankAtmRepository;
    private final BankService bankService;
    private final BankOfficeService bankOfficeService;
    private final EmployeeService employeeService;

    public BankAtm createBankAtm(BankAtmRequest bankAtmRequest) {
        Bank bank = bankService.getBankById(bankAtmRequest.getBank_id());
        BankAtm bankAtm = new BankAtm(bankAtmRequest.getName(), bankAtmRequest.getAddress(), bank,
                bankOfficeService.getBankOfficeById(bankAtmRequest.getLocation_id()),
                employeeService.getEmployeeById(bankAtmRequest.getEmployee_id()),
                bankAtmRequest.isCanDispenseCash(), bankAtmRequest.isCanAcceptDeposits());
        bankAtm.setMaintenanceCost(generateMaintenanceCost());
        bankAtm.setStatus(BankAtmStatusEnum.randomStatus());
        bankAtm.setCashAmount(generateAtmMoney(bank));
        return bankAtmRepository.save(bankAtm);
    }

    private double generateAtmMoney(Bank bank) {
        return new Random().nextDouble(bank.getTotalCash());
    }

    private double generateMaintenanceCost() {
        Random rand = new Random();
        return rand.nextDouble() * 1000; // сумма до 1000000
    }


    public BankAtm getBankAtmById(int id) {
        return bankAtmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BankAtm was not found"));
    }

    public BankAtm getBankAtmDtoById(int id) {
        return getBankAtmById(id);
    }

    public List<BankAtm> getAllBankAtms() {
        return bankAtmRepository.findAll();
    }

    public List<BankAtm> getAllBankAtmsByBankId(int bankId) {
        return bankAtmRepository.findAllByBankId(bankId);
    }

    public BankAtm updateBankAtm(int id, String name) {
        BankAtm bankAtm = getBankAtmById(id);
        bankAtm.setName(name);
        return bankAtmRepository.save(bankAtm);
    }

    public void deleteBankAtm(int id) {
        bankAtmRepository.deleteById(id);
    }
}
