package com.mtrifonov.vacationpaycalculator.components;

import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.mtrifonov.vacationpaycalculator.domain.Holiday;
import com.mtrifonov.vacationpaycalculator.domain.VacationInfo;

/**
 *
 * @Mikhail Trifonov
 */
@Component
@NoArgsConstructor
public class HolidayCalendar {

    private final List<Holiday> holidays = new ArrayList<>();

    public void addHolidays(Holiday... arr) {

        for (var h : arr) {
            holidays.add(h);
        }

        holidays.sort(Holiday.HOLIDAY_COMPARATOR);
    }
    
    public List<Holiday> calculateWorkingDays(VacationInfo info) {

        var firstDay = info.getFirstDay().get();
        var lastDay = info.getLastDay().get();

        var result = Holiday.holidaysBetween(firstDay, lastDay, holidays);
        info.setNumberOfDays(info.getNumberOfDays() - result.size());
        return result;
    }
}
