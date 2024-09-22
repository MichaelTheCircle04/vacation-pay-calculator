
package com.mtrifonov.vacationpaycalculator.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @Mikhail Trifonov
 */
@ControllerAdvice
public class IllegalArgumentExceptionAdvice {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(Model model, IllegalArgumentException e) {
        String message = e.getMessage();
        model.addAttribute("exception", message);
        return "badInformationForm";
    }
}
