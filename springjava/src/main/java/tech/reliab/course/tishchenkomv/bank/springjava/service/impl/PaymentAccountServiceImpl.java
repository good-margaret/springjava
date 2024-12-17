package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.PaymentAccount;
import tech.reliab.course.tishchenkomv.bank.springjava.model.PaymentAccountRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.PaymentAccountRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.PaymentAccountService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.UserService;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;
    private final UserService userService;
    private final BankService bankService;

    public PaymentAccount createPaymentAccount(PaymentAccountRequest paymentAccountRequest) {
        PaymentAccount paymentAccount = new PaymentAccount(userService.getUserById(paymentAccountRequest.getUser_id()),
                bankService.getBankById(paymentAccountRequest.getBank_id()));
        return paymentAccountRepository.save(paymentAccount);
    }

    public PaymentAccount getPaymentAccountById(int id) {
        return paymentAccountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("PaymentAccount was not found"));
    }

    public PaymentAccount getPaymentAccountDtoById(int id) {
        return getPaymentAccountById(id);
    }

    public List<PaymentAccount> getAllPaymentAccounts() {
        return paymentAccountRepository.findAll();
    }

    public PaymentAccount updatePaymentAccount(int id, int bankId) {
        PaymentAccount paymentAccount = getPaymentAccountById(id);
        paymentAccount.setBank(bankService.getBankById(bankId));
        return paymentAccountRepository.save(paymentAccount);
    }

    public void deletePaymentAccount(int id) {
        paymentAccountRepository.deleteById(id);
    }
}
