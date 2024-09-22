
package com.mtrifonov.vacationpaycalculator.controllers;

import com.mtrifonov.vacationpaycalculator.Calculator;
import com.mtrifonov.vacationpaycalculator.VacationInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Mikhail Trifonov
 */
@Controller
@RequestMapping("/calculate")
public class CalculatorController {
    
    private final Calculator calculator;
            
    @Autowired
    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }
      
    @GetMapping
    public String produceMainForm() {
        return "mainForm";
    }
    
    @PostMapping("/customize")
    public String produceCustomForm(HttpServletRequest request) {
        if (request.getParameter("inf").equals("yes")) {
            return "formWithDates";
        }
        return "formWithoutDates";
    }
    
    @PostMapping
    public String handleForm(HttpServletRequest request, Model model) { 
        VacationInfo info = VacationInfo.initializeVacationInfo(request.getParameterMap());
        Double cash = calculator.calculate(info, model);
        model.addAttribute("cash", cash.intValue());
        return "calculations";   
    }
}
