package com.mtrifonov.vacationpaycalculator.components;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.mtrifonov.vacationpaycalculator.domain.CalculationsDTO;
import com.mtrifonov.vacationpaycalculator.domain.VacationInfo;

/**
 *
 * @Mikhail Trifonov
 */
@Component
@AllArgsConstructor
public class Calculator {
    
    private final HolidayCalendar holidayCalendar;

    public CalculationsDTO calculate(VacationInfo info) {
        
        var first = info.getFirstDay();

        if (first.isEmpty()) {
            
            Double dayPayment = info.getAverageSalary() / 29.3;
            Double totalAmount = dayPayment * info.getNumberOfDays();
            return CalculationsDTO.builder().totalAmount(totalAmount.intValue()).build();
        } else {
            
            var holidays = holidayCalendar.calculateWorkingDays(info);
            Double dayPayment = info.getAverageSalary() / 29.3;
            Double totalAmount = dayPayment * info.getNumberOfDays();
            return CalculationsDTO.builder()
                .totalAmount(totalAmount.intValue())
                .holidays(holidays).build();
        }
    }
}
