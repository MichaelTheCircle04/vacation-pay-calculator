
package com.mtrifonov.vacationpaycalculator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Mikhail Trifonov
 */
@Component
public class HolidayCalendar {
    private final ObjectMapper mapper;
    private List<Holiday> holidays;

    @Autowired
    public HolidayCalendar(ObjectMapper mapper) {
        this.mapper = mapper;
    }
       
    @PostConstruct
    public void init() throws IOException {
        File src = new File("src/main/resources/holidays.json");
        holidays = mapper.readValue(src, new TypeReference<List<Holiday>>(){});
    }
    
    public List<Holiday> calculateWorkingDays(VacationInfo info) {
        LocalDate firstDay = info.getFirstDay();
        LocalDate lastDay = info.getLastDay();
        if (firstDay.getYear() == lastDay.getYear()) {
            return calculateIfSameYear(info, firstDay, lastDay);
        }
        return calculateIfDifferentYear(info, firstDay, lastDay);
    }
    
    public List<Holiday> calculateIfSameYear(VacationInfo info, LocalDate firstDay, LocalDate lastDay) {
        int day;
        int month;
        List<Holiday> holidaysDuringVacation = new ArrayList<>();
        for (Holiday holiday: holidays) {
            day = holiday.getDay();
            month = holiday.getMonth();
            if (month < firstDay.getMonthValue() || (month == firstDay.getMonthValue() && day < firstDay.getDayOfMonth())) {
                continue;
            }
            if (month > lastDay.getMonthValue()) {
                break;
            } else if (month == lastDay.getMonthValue() && day > lastDay.getDayOfMonth()) {
                break;
            }
            holidaysDuringVacation.add(holiday);
            info.setNumberOfDays(info.getNumberOfDays() - 1);
        }
        return holidaysDuringVacation;
    }
    public List<Holiday> calculateIfDifferentYear(VacationInfo info, LocalDate firstDay, LocalDate lastDay) {
        int day;
        int month;
        List<Holiday> holidaysDuringVacation = new ArrayList<>();
        for (Holiday holiday: holidays) {
            day = holiday.getDay();
            month = holiday.getMonth();
            if (month < firstDay.getMonthValue() && month > lastDay.getMonthValue()){
                continue;
            }
            if (month < firstDay.getMonthValue() && (month == lastDay.getMonthValue() && day > lastDay.getDayOfMonth())){
                continue;
            }
            if ((month == firstDay.getMonthValue() && day < firstDay.getDayOfMonth()) && month > lastDay.getMonthValue()){
                continue;
            }
            if ((month == firstDay.getMonthValue() && day < firstDay.getDayOfMonth()) && (month == lastDay.getMonthValue() && day > lastDay.getDayOfMonth())){
                continue;
            }                                    
            holidaysDuringVacation.add(holiday);
            info.setNumberOfDays(info.getNumberOfDays() - 1);
        }
        return holidaysDuringVacation;
    }
    
}
