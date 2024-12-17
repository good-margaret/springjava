package tech.reliab.course.tishchenkomv.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tech.reliab.course.tishchenkomv.bank.springjava.SpringjavaApplication;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.BankRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tech.reliab.course.tishchenkomv.bank.springjava.service.impl.BankServiceImpl;

@SpringBootTest(classes = SpringjavaApplication.class)
class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImpl bankService;

    private Bank testBank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBank = new Bank("Test Bank");
        testBank.setId(1);
        testBank.setRating(80);
        testBank.setTotalCash(500_000);
        testBank.setInterestRate(5.0);
    }

    /**
     * Вспомогательный метод для создания тестового банка
     */
    private Bank createTestBank(int id, String name) {
        Bank bank = new Bank(name);
        bank.setId(id);
        return bank;
    }

    @Test
    void testCreateBank() {
        when(bankRepository.save(any(Bank.class))).thenAnswer(invocation -> {
            Bank bank = invocation.getArgument(0);
            bank.setId(1);
            return bank;
        });

        Bank createdBank = bankService.createBank("Test Bank");

        assertThat(createdBank).isNotNull();
        assertThat(createdBank.getId()).isEqualTo(1);
        assertThat(createdBank.getName()).isEqualTo("Test Bank");
        assertThat(createdBank.getRating()).isBetween(0, 100);
        assertThat(createdBank.getTotalCash()).isBetween(0.0, 1_000_000.0);
        assertThat(createdBank.getInterestRate()).isBetween(0.0, 20.0);

        verify(bankRepository).save(any(Bank.class));
    }

    @Test
    void testGetBankById() {
        when(bankRepository.findById(1)).thenReturn(Optional.of(testBank));

        Bank bank = bankService.getBankById(1);

        assertThat(bank).isNotNull();
        assertThat(bank.getId()).isEqualTo(1);
        assertThat(bank.getName()).isEqualTo("Test Bank");

        verify(bankRepository).findById(1);
    }

    @Test
    void testGetBankById_NotFound() {
        when(bankRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bankService.getBankById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Bank was not found");

        verify(bankRepository).findById(1);
    }

    @Test
    void testGetAllBanks() {
        List<Bank> banks = List.of(
                createTestBank(1, "Bank 1"),
                createTestBank(2, "Bank 2")
        );
        when(bankRepository.findAll()).thenReturn(banks);

        List<Bank> result = bankService.getAllBanks();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Bank 1");
        assertThat(result.get(1).getName()).isEqualTo("Bank 2");

        verify(bankRepository).findAll();
    }

    @Test
    void testUpdateBank() {
        when(bankRepository.findById(1)).thenReturn(Optional.of(testBank));
        when(bankRepository.save(any(Bank.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Bank updatedBank = bankService.updateBank(1, "Updated Bank");

        assertThat(updatedBank).isNotNull();
        assertThat(updatedBank.getName()).isEqualTo("Updated Bank");

        verify(bankRepository).findById(1);
        verify(bankRepository).save(testBank);
    }

    @Test
    void testUpdateBank_NotFound() {
        when(bankRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bankService.updateBank(1, "Updated Bank"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Bank was not found");

        verify(bankRepository).findById(1);
        verify(bankRepository, never()).save(any(Bank.class));
    }

    @Test
    void testDeleteBank() {
        doNothing().when(bankRepository).deleteById(1);

        bankService.deleteBank(1);

        verify(bankRepository).deleteById(1);
    }
}
