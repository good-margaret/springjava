package tech.reliab.course.tishchenkomv.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tech.reliab.course.tishchenkomv.bank.springjava.SpringjavaApplication;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.CreditAccount;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Employee;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.PaymentAccount;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.User;
import tech.reliab.course.tishchenkomv.bank.springjava.model.CreditAccountRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.CreditAccountRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.*;
import tech.reliab.course.tishchenkomv.bank.springjava.service.impl.CreditAccountServiceImpl;
import tech.reliab.course.tishchenkomv.container.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = SpringjavaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
class CreditAccountServiceTest {

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PaymentAccountService paymentAccountService;

    @Autowired
    private CreditAccountServiceImpl creditAccountService;

    private CreditAccount testCreditAccount;
    private Bank testBank;
    private User testUser;
    private Employee testEmployee;
    private PaymentAccount testPaymentAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);
        testBank.setInterestRate(10.0);

        testUser = new User();
        testUser.setId(1);
        testUser.setFullName("John Doe");

        testEmployee = new Employee();
        testEmployee.setId(1);
        testEmployee.setFullName("Jane Smith");

        testPaymentAccount = new PaymentAccount();
        testPaymentAccount.setId(1);
        testPaymentAccount.setBalance(5000.0);

        testCreditAccount = new CreditAccount(
                testUser, testBank, LocalDate.now(), 12, 10000.0, 10.0, testEmployee, testPaymentAccount
        );
        testCreditAccount.setId(1);
    }

    @Test
    void testCreateCreditAccount() {
        // Arrange
        CreditAccountRequest request = new CreditAccountRequest(
                1, 1, LocalDate.now(), LocalDate.now(), 12, 10000.0, 1000, 15.0, 1, 1
        );

        when(userService.getUserById(1)).thenReturn(testUser);
        when(bankService.getBankById(1)).thenReturn(testBank);
        when(employeeService.getEmployeeById(1)).thenReturn(testEmployee);
        when(paymentAccountService.getPaymentAccountById(1)).thenReturn(testPaymentAccount);
        when(creditAccountRepository.save(any(CreditAccount.class))).thenAnswer(invocation -> {
            CreditAccount account = invocation.getArgument(0);
            account.setId(1);
            return account;
        });

        // Act
        CreditAccount createdAccount = creditAccountService.createCreditAccount(request);

        // Assert
        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getId()).isEqualTo(1);
        assertThat(createdAccount.getUser()).isEqualTo(testUser);
        assertThat(createdAccount.getBank()).isEqualTo(testBank);
        assertThat(createdAccount.getInterestRate()).isEqualTo(10.0); // Корректировка ставки
        assertThat(createdAccount.getMonthlyPayment()).isPositive();

        verify(creditAccountRepository).save(any(CreditAccount.class));
        verify(userService).getUserById(1);
        verify(bankService).getBankById(1);
        verify(employeeService).getEmployeeById(1);
        verify(paymentAccountService).getPaymentAccountById(1);
    }

    @Test
    void testGetCreditAccountById_Success() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.of(testCreditAccount));

        // Act
        CreditAccount result = creditAccountService.getCreditAccountById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(creditAccountRepository).findById(1);
    }

    @Test
    void testGetCreditAccountById_NotFound() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> creditAccountService.getCreditAccountById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("CreditAccount was not found");

        verify(creditAccountRepository).findById(1);
    }

    @Test
    void testGetAllCreditAccounts() {
        // Arrange
        when(creditAccountRepository.findAll()).thenReturn(List.of(testCreditAccount));

        // Act
        List<CreditAccount> result = creditAccountService.getAllCreditAccounts();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);

        verify(creditAccountRepository).findAll();
    }

    @Test
    void testUpdateCreditAccount_Success() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.of(testCreditAccount));
        when(bankService.getBankById(1)).thenReturn(testBank);
        when(creditAccountRepository.save(any(CreditAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CreditAccount updatedAccount = creditAccountService.updateCreditAccount(1, 1);

        // Assert
        assertThat(updatedAccount).isNotNull();
        assertThat(updatedAccount.getBank()).isEqualTo(testBank);

        verify(creditAccountRepository).findById(1);
        verify(bankService).getBankById(1);
        verify(creditAccountRepository).save(any(CreditAccount.class));
    }

    @Test
    void testUpdateCreditAccount_NotFound() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> creditAccountService.updateCreditAccount(1, 1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("CreditAccount was not found");

        verify(creditAccountRepository).findById(1);
        verify(bankService, never()).getBankById(anyInt());
    }

    @Test
    void testDeleteCreditAccount() {
        // Arrange
        doNothing().when(creditAccountRepository).deleteById(1);

        // Act
        creditAccountService.deleteCreditAccount(1);

        // Assert
        verify(creditAccountRepository).deleteById(1);
    }
}
