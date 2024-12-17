package tech.reliab.course.tishchenkomv.bank.springjava.model;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private String fullName;
    private LocalDate birthDate;
    private String position;
    private int bank_id;
    private boolean worksRemotely;
    private int bankOffice_id;
    private boolean canIssueCredits;
    private double salary;
}
