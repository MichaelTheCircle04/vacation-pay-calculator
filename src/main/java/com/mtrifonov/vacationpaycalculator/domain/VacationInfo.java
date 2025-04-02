package com.mtrifonov.vacationpaycalculator.domain;

import java.time.LocalDate;
import java.util.Optional;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @Mikhail Trifonov
 */
@Data
@NoArgsConstructor
public class VacationInfo {
    
    @NotNull
    private Integer averageSalary;
    private Integer numberOfDays;
    private Optional<LocalDate> firstDay;
    private Optional<LocalDate> lastDay;
}
