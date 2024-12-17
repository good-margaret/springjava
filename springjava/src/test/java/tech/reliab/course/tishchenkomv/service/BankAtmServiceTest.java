package tech.reliab.course.tishchenkomv.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tech.reliab.course.tishchenkomv.bank.springjava.SpringjavaApplication;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Bank;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankAtm;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankOffice;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Employee;
import tech.reliab.course.tishchenkomv.bank.springjava.enums.BankAtmStatusEnum;
import tech.reliab.course.tishchenkomv.bank.springjava.model.BankAtmRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.BankAtmRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tech.reliab.course.tishchenkomv.bank.springjava.service.BankOfficeService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.EmployeeService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.impl.BankAtmServiceImpl;

@SpringBootTest(classes = SpringjavaApplication.class)
class BankAtmServiceTest {

    @Mock
    private BankAtmRepository bankAtmRepository;

    @Mock
    private BankService bankService;

    @Mock
    private BankOfficeService bankOfficeService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private BankAtmServiceImpl bankAtmService;

    private Bank testBank;
    private BankAtm testAtm;
    private BankOffice testOffice;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);
        testBank.setTotalCash(1_000_000.0);

        testOffice = new BankOffice();
        testOffice.setId(1);
        testOffice.setName("Main Office");

        testEmployee = new Employee();
        testEmployee.setId(1);
        testEmployee.setFullName("John Doe");

        testAtm = new BankAtm("ATM-1", "123 Test St", testBank, testOffice, testEmployee, true, true);
        testAtm.setId(1);
        testAtm.setMaintenanceCost(500);
        testAtm.setStatus(BankAtmStatusEnum.WORKING);
    }

    @Test
    void testCreateBankAtm() {
        BankAtmRequest request = new BankAtmRequest("ATM-1", "123 Test St", 1, 1, 1, true, true, 0, 0);

        when(bankService.getBankById(1)).thenReturn(testBank);
        when(bankOfficeService.getBankOfficeById(1)).thenReturn(testOffice);
        when(employeeService.getEmployeeById(1)).thenReturn(testEmployee);
        when(bankAtmRepository.save(any(BankAtm.class))).thenAnswer(invocation -> {
            BankAtm atm = invocation.getArgument(0);
            atm.setId(1);
            return atm;
        });

        BankAtm createdAtm = bankAtmService.createBankAtm(request);

        assertThat(createdAtm).isNotNull();
        assertThat(createdAtm.getId()).isEqualTo(1);
        assertThat(createdAtm.getName()).isEqualTo("ATM-1");
        assertThat(createdAtm.getBank()).isEqualTo(testBank);
        assertThat(createdAtm.getStatus()).isIn(BankAtmStatusEnum.values());

        verify(bankAtmRepository).save(any(BankAtm.class));
        verify(bankService).getBankById(1);
        verify(bankOfficeService).getBankOfficeById(1);
        verify(employeeService).getEmployeeById(1);
    }

    @Test
    void testGetBankAtmById() {
        when(bankAtmRepository.findById(1)).thenReturn(Optional.of(testAtm));

        BankAtm result = bankAtmService.getBankAtmById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("ATM-1");

        verify(bankAtmRepository).findById(1);
    }

    @Test
    void testGetBankAtmById_NotFound() {
        when(bankAtmRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bankAtmService.getBankAtmById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("BankAtm was not found");

        verify(bankAtmRepository).findById(1);
    }

    @Test
    void testGetAllBankAtms() {
        when(bankAtmRepository.findAll()).thenReturn(List.of(testAtm));

        List<BankAtm> result = bankAtmService.getAllBankAtms();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("ATM-1");

        verify(bankAtmRepository).findAll();
    }

    @Test
    void testUpdateBankAtm() {
        when(bankAtmRepository.findById(1)).thenReturn(Optional.of(testAtm));
        when(bankAtmRepository.save(any(BankAtm.class))).thenReturn(testAtm);

        BankAtm updatedAtm = bankAtmService.updateBankAtm(1, "Updated ATM");

        assertThat(updatedAtm).isNotNull();
        assertThat(updatedAtm.getName()).isEqualTo("Updated ATM");

        verify(bankAtmRepository).findById(1);
        verify(bankAtmRepository).save(any(BankAtm.class));
    }

    @Test
    void testDeleteBankAtm() {
        doNothing().when(bankAtmRepository).deleteById(1);

        bankAtmService.deleteBankAtm(1);

        verify(bankAtmRepository).deleteById(1);
    }

    @Test
    void testGetAllBankAtmsByBankId() {
        when(bankAtmRepository.findAllByBankId(1)).thenReturn(List.of(testAtm));

        List<BankAtm> result = bankAtmService.getAllBankAtmsByBankId(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBank()).isEqualTo(testBank);

        verify(bankAtmRepository).findAllByBankId(1);
    }
}
