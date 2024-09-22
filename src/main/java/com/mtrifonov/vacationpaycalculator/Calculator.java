
package com.mtrifonov.vacationpaycalculator;

import com.mtrifonov.vacationpaycalculator.validators.DateValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 *
 * @Mikhail Trifonov
 */
@Component
public class Calculator {
    
    private final HolidayCalendar holidayCalendar;
    private final DateValidator dateValidator;
    
    @Autowired
    public Calculator(DateValidator dateValidator, HolidayCalendar holidayCalendar) {
        this.dateValidator = dateValidator;
        this.holidayCalendar = holidayCalendar;
    }
    public double calculate(VacationInfo info, Model model) {
        
        if (info.getFirstDay() != null && info.getLastDay() != null) {
            dateValidator.validate(info);
            List<Holiday> holidays = holidayCalendar.calculateWorkingDays(info);
            model.addAttribute("holidays", holidays);
        }
        double cash = info.getAverageSalary() / 29.3;
        return cash * info.getNumberOfDays();
    }
}
