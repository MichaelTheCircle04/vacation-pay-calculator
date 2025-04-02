package com.mtrifonov.vacationpaycalculator.controllers;

import com.mtrifonov.vacationpaycalculator.components.Calculator;
import com.mtrifonov.vacationpaycalculator.domain.CalculationsDTO;
import com.mtrifonov.vacationpaycalculator.domain.VacationInfo;
import com.mtrifonov.vacationpaycalculator.validators.VacationInfoValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.time.temporal.ChronoUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Mikhail Trifonov
 */
@Controller
@RequestMapping("/calculate")
@AllArgsConstructor
public class CalculatorController {
    
    private final Calculator calculator;
      
    @GetMapping
    public String produceMainPage() {
        return "main";
    }

    @PostMapping
    public ResponseEntity<CalculationsDTO> calculate(@Valid @RequestBody VacationInfo info) {

        VacationInfoValidator.validate(info);

        if (info.getFirstDay().isPresent()) {
            info.setNumberOfDays((int) ChronoUnit.DAYS.between(info.getFirstDay().get(), info.getLastDay().get()));
        }

        var result = calculator.calculate(info);
        return ResponseEntity.ok(result);
    }
}
