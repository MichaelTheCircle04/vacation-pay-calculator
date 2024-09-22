
package com.mtrifonov.vacationpaycalculator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @Mikhail Trifonov
 */
@Data
@NoArgsConstructor
public class VacationInfo {
    
    private Integer averageSalary;
    private Integer numberOfDays;
    private LocalDate firstDay;
    private LocalDate lastDay;
   
    public static VacationInfo initializeVacationInfo(Map<String, String[]> requestParameters) {
        VacationInfo vacationInfo = new VacationInfo();
        vacationInfo.setAverageSalary(Integer.valueOf(requestParameters.get("averageSalary")[0]));
        if (requestParameters.containsKey("numberOfDays")) {
            vacationInfo.setNumberOfDays(Integer.valueOf(requestParameters.get("numberOfDays")[0]));
            return vacationInfo;
        }
        vacationInfo.setFirstDay(LocalDate.parse(requestParameters.get("firstDay")[0]));
        vacationInfo.setLastDay(LocalDate.parse(requestParameters.get("lastDay")[0]));
        vacationInfo.setNumberOfDays((int)ChronoUnit.DAYS.between(vacationInfo.getFirstDay(), vacationInfo.getLastDay()));
        return vacationInfo;
    }
}
