package com.mtrifonov.vacationpaycalculator.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @Mikhail Trifonov
 */
@Data
@NoArgsConstructor
public class Holiday {

    private String nameHoliday;
    private LocalDate date;

    public static final Comparator<Holiday> HOLIDAY_COMPARATOR = (h1, h2) -> {
        return h1.getDate().compareTo(h2.getDate());
        };
    
    public static List<Holiday> holidaysBetween(LocalDate firstDay, LocalDate lastDay, List<Holiday> holidays) {

        List<LocalDate> dates = holidays.stream().map(h -> h.getDate()).toList();
        int first = -1;

        int l = 0;
        int r = dates.size() - 1;
        while (l <= r) { //нужно найти самый близкий больший или равный элемент

            if (l == r) {
                if (dates.get(r).compareTo(firstDay) >= 0) {
                    first = r;
                } else {
                    first = r + 1;
                }
                break;
            }

            int m = (l + r) / 2;
            var cur = dates.get(m);
            
            if (cur.compareTo(firstDay) > 0) {
                r = m - 1; 
            } else if (cur.compareTo(firstDay) == 0) {
                first = m;
                break;
            } else {
                l = m + 1;
            }
        }

        if (first == dates.size()) {
            return Collections.emptyList();
        }

        var result = new ArrayList<Holiday>();

        int i = first;
        while (dates.get(i).compareTo(lastDay) <= 0) {
            result.add(holidays.get(i));
            i++;
        }

        return result;
    }
}
