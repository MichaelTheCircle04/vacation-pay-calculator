
package com.mtrifonov.vacationpaycalculator.validators;

import com.mtrifonov.vacationpaycalculator.VacationInfo;
import org.springframework.stereotype.Component;

/**
 *
 * @Mikhail Trifonov
 */
@Component
public class DateValidatorImpl implements DateValidator {

    @Override
    public boolean validate(VacationInfo vacationInfo) {
        
        if (vacationInfo.getFirstDay() == null || vacationInfo.getLastDay() == null) {
            return true;
        }
        if (vacationInfo.getLastDay().compareTo(vacationInfo.getFirstDay()) < 0) {
            throw new IllegalArgumentException("Дата окончания отпуска раньше даты начала.");
        } 
        return true;
    }
    
}
