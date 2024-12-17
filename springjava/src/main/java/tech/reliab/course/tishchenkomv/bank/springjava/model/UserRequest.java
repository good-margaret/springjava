package tech.reliab.course.tishchenkomv.bank.springjava.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String fullName;
    private LocalDate birthDate;
    private String job;
}
