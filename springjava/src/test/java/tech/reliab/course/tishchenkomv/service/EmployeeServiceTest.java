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
import tech.reliab.course.tishchenkomv.bank.springjava.entity.BankOffice;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Employee;
import tech.reliab.course.tishchenkomv.bank.springjava.model.EmployeeRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.EmployeeRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankOfficeService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.impl.EmployeeServiceImpl;
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
class EmployeeServiceTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private BankOfficeService bankOfficeService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    private Employee testEmployee;
    private Bank testBank;
    private BankOffice testBankOffice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);

        testBankOffice = new BankOffice();
        testBankOffice.setId(1);
        testBankOffice.setName("Main Office");

        testEmployee = new Employee("John Doe", LocalDate.of(1990, 1, 1), "Manager", false, true, testBank, testBankOffice, 50000.0);
        testEmployee.setId(1);
    }

    @Test
    void testCreateEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest(
                "John Doe", LocalDate.of(1990, 1, 1), "Manager", 1, true, 1, true, 50000.0
        );

        when(bankService.getBankById(1)).thenReturn(testBank);
        when(bankOfficeService.getBankOfficeById(1)).thenReturn(testBankOffice);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee employee = invocation.getArgument(0);
            employee.setId(1);
            return employee;
        });

        // Act
        Employee createdEmployee = employeeService.createEmployee(request);

        // Assert
        assertThat(createdEmployee).isNotNull();
        assertThat(createdEmployee.getId()).isEqualTo(1);
        assertThat(createdEmployee.getFullName()).isEqualTo("John Doe");
        assertThat(createdEmployee.getBank()).isEqualTo(testBank);
        assertThat(createdEmployee.getBankOffice()).isEqualTo(testBankOffice);
        assertThat(createdEmployee.getSalary()).isEqualTo(50000.0);

        verify(bankService).getBankById(1);
        verify(bankOfficeService).getBankOfficeById(1);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById_Success() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));

        // Act
        Employee result = employeeService.getEmployeeById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFullName()).isEqualTo("John Doe");

        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.getEmployeeById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Employee was not found");

        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetEmployeeDtoById() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));

        // Act
        Employee result = employeeService.getEmployeeDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(List.of(testEmployee));

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullName()).isEqualTo("John Doe");

        verify(employeeRepository).findAll();
    }

    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Employee updatedEmployee = employeeService.updateEmployee(1, "Jane Doe");

        // Assert
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFullName()).isEqualTo("Jane Doe");

        verify(employeeRepository).findById(1);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.updateEmployee(1, "Jane Doe"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Employee was not found");

        verify(employeeRepository).findById(1);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        doNothing().when(employeeRepository).deleteById(1);

        // Act
        employeeService.deleteEmployee(1);

        // Assert
        verify(employeeRepository).deleteById(1);
    }
}