package com.mtrifonov.vacationpaycalculator.validators;

import com.mtrifonov.vacationpaycalculator.domain.VacationInfo;

/**
 *
 * @Mikhail Trifonov
 */
public class VacationInfoValidator {

    public static void validate(VacationInfo info) {

        var first = info.getFirstDay();
        var last = info.getLastDay(); 

        if ((first.isEmpty() && last.isPresent()) || (first.isPresent() && last.isEmpty())) {
            throw new IllegalArgumentException("Нельзя предоставить только одну дату");
        }

        if (first.isPresent() && last.get().compareTo(first.get()) < 0) {
            throw new IllegalArgumentException("Дата окончания отпуска раньше даты начала");
        } 

        if ((first.isEmpty() && last.isEmpty()) && info.getNumberOfDays() == null) {
            throw new IllegalArgumentException("Необходимо предоставить точные даты, либо колличество дней");
        }
    }
    
}
