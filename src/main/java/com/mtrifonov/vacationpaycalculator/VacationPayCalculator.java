package com.mtrifonov.vacationpaycalculator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtrifonov.vacationpaycalculator.components.HolidayCalendar;
import com.mtrifonov.vacationpaycalculator.domain.Holiday;

/**
 *
 * @Mikhail Trifonov
 */
@SpringBootApplication
public class VacationPayCalculator {

    public static void main(String[] args) {
        SpringApplication.run(VacationPayCalculator.class, args);        
    }

    @Bean
    public CommandLineRunner commandLineRunner(HolidayCalendar calendar, ObjectMapper mapper) {
        
        return args -> {
            var src = new ClassPathResource("holidays.json");
            var holidays = mapper.readValue(src.getFile(), new TypeReference<Holiday[]>(){});
            calendar.addHolidays(holidays);
        };
    }
}
