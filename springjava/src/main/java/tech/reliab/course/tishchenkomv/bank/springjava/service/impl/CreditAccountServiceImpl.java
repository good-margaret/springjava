package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.CreditAccount;
import tech.reliab.course.tishchenkomv.bank.springjava.model.CreditAccountRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.CreditAccountRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class CreditAccountServiceImpl implements CreditAccountService {

    private final CreditAccountRepository creditAccountRepository;
    private final BankService bankService;
    private final UserService userService;
    private final EmployeeService employeeService;
    private final PaymentAccountService paymentAccountService;

    public CreditAccount createCreditAccount(CreditAccountRequest creditAccountRequest) {
        CreditAccount creditAccount = new CreditAccount(userService.getUserById(creditAccountRequest.getUser_id()),
                bankService.getBankById(creditAccountRequest.getBank_id()), creditAccountRequest.getStartDate(),
                creditAccountRequest.getCreditMonths(), creditAccountRequest.getCreditAmount(), creditAccountRequest.getInterestRate(),
                employeeService.getEmployeeById(creditAccountRequest.getEmployee_id()),
                paymentAccountService.getPaymentAccountById(creditAccountRequest.getPaymentAccount_id()));

        creditAccount.setEndDate(calculateEndDate(creditAccountRequest.getStartDate(), creditAccountRequest.getCreditMonths()));
        creditAccount.setMonthlyPayment(calculateMonthlyPayment(creditAccountRequest.getInterestRate(),
                creditAccountRequest.getCreditAmount(), creditAccountRequest.getCreditMonths()));
        creditAccount.setInterestRate(calculateInterestRate(creditAccountRequest.getInterestRate(), creditAccountRequest.getBank_id()));
        return creditAccountRepository.save(creditAccount);
    }

    private LocalDate calculateEndDate(LocalDate startDate, int months) {
        return startDate.plusMonths(months);
    }

    private double calculateMonthlyPayment(double creditAmount, double interestRate, int months) {
        double monthlyRate = interestRate / 100 / 12;
        return (creditAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    private double calculateInterestRate(double interestRate, int bankId) {
        Bank bank = bankService.getBankById(bankId);
        if (interestRate > bank.getInterestRate()) {
            System.out.println("Заданная процентная ставка превышает процентную ставку банка. Ставка будет скорректирована.");
            interestRate = bank.getInterestRate();
        }
        return interestRate;
    }

    public CreditAccount getCreditAccountById(int id) {
        return creditAccountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("CreditAccount was not found"));
    }

    public CreditAccount getCreditAccountDtoById(int id) {
        return getCreditAccountById(id);
    }

    public List<CreditAccount> getAllCreditAccounts() {
        return creditAccountRepository.findAll();
    }

    public CreditAccount updateCreditAccount(int id, int bankId) {
        CreditAccount creditAccount = getCreditAccountById(id);
        creditAccount.setBank(bankService.getBankById(bankId));
        return creditAccountRepository.save(creditAccount);
    }

    public void deleteCreditAccount(int id) {
        creditAccountRepository.deleteById(id);
    }
}
