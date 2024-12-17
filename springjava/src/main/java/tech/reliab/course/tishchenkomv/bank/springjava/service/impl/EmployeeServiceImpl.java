package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.Employee;
import tech.reliab.course.tishchenkomv.bank.springjava.model.EmployeeRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.EmployeeRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankOfficeService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.BankService;
import tech.reliab.course.tishchenkomv.bank.springjava.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BankService bankService;
    private final BankOfficeService bankOfficeService;

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee(employeeRequest.getFullName(), employeeRequest.getBirthDate(),
                employeeRequest.getPosition(), employeeRequest.isWorksRemotely(), employeeRequest.isCanIssueCredits(),
                bankService.getBankById(employeeRequest.getBank_id()),
                bankOfficeService.getBankOfficeById(employeeRequest.getBankOffice_id()),
                 employeeRequest.getSalary());
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeDtoById(int id) {
        return getEmployeeById(id);
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Employee was not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(int id, String name) {
        Employee employee = getEmployeeById(id);
        employee.setFullName(name);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
