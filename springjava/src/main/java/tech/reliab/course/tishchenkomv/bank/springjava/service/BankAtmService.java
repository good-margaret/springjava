package tech.reliab.course.tishchenkomv.bank.springjava.service;


import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankAtm;
import tech.reliab.course.tishchenkomv.bank.springjava.model.BankAtmRequest;

import java.util.List;

public interface BankAtmService {
    BankAtm createBankAtm(BankAtmRequest bankAtmRequest);

    BankAtm getBankAtmById(int id);

    BankAtm getBankAtmDtoById(int id);

    List<BankAtm> getAllBankAtms();

    List<BankAtm> getAllBankAtmsByBankId(int bankId);

    BankAtm updateBankAtm(int id, String name);

    void deleteBankAtm(int id);
}
