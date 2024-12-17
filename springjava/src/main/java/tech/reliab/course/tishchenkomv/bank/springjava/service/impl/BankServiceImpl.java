package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.BankRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    public Bank createBank(String bankName) {
        Bank bank = new Bank(bankName);
        bank.setRating(generateRating());
        bank.setTotalCash(generateTotalCash());
        bank.setInterestRate(generateInterestRate(bank.getRating()));
        return bankRepository.save(bank);
    }

    private int generateRating() {
        Random rand = new Random();
        return rand.nextInt(101); // рейтинг от 0 до 100
    }

    private double generateTotalCash() {
        Random rand = new Random();
        return rand.nextDouble() * 1000000; // сумма до 1000000
    }

    private double generateInterestRate(int rating) {
        Random rand = new Random();
        double baseRate = rand.nextDouble() * 20;

        if (rating > 0)
            baseRate = baseRate * (100 - rating) / 100;

        return Math.min(baseRate, 20);
    }

    public Bank getBankDtoById(int id) {
        return getBankById(id);
    }

    public Bank getBankById(int id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Bank was not found"));
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank updateBank(int id, String name) {
        Bank bank = getBankById(id);
        bank.setName(name);
        return bankRepository.save(bank);
    }

    public void deleteBank(int id) {
        bankRepository.deleteById(id);
    }
}
