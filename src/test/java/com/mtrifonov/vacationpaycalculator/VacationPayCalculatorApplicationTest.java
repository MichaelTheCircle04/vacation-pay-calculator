/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtrifonov.vacationpaycalculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 *
 * @Mikhail Trifonov
 */
@SpringBootTest
@AutoConfigureMockMvc
public class VacationPayCalculatorApplicationTest {
    
    @Autowired
    private MockMvc mvc;
                
    @Test
    void contextLoads() {
    }
    
    @Test
    void testProduceMainFormMethod() throws Exception {
        mvc.perform(get("/calculate")).andExpect(status().isOk()).andExpect(view().name("mainForm"));
    }
    @Test
    void testProduceCustomFormMethod() throws Exception {
        mvc.perform(post("/calculate/customize?inf=yes")).andExpect(status().isOk()).andExpect(view().name("formWithDates"));
        mvc.perform(post("/calculate/customize?inf=no")).andExpect(status().isOk()).andExpect(view().name("formWithoutDates"));
    }
    @Test
    void testHandleFormMethod() throws Exception {
        mvc.perform(post("/calculate?averageSalary=80000&firstDay=2024-10-01&lastDay=2024-10-11")).andExpect(model().attribute("cash", 27303));
        mvc.perform(post("/calculate?averageSalary=80000&numberOfDays=10")).andExpect(model().attribute("cash", 27303));
        mvc.perform(post("/calculate?averageSalary=80000&firstDay=2024-01-01&lastDay=2024-01-21")).andExpect(model().attribute("cash", 32764));
        mvc.perform(post("/calculate?averageSalary=80000&numberOfDays=20")).andExpect(model().attribute("cash", 54607));
        mvc.perform(post("/calculate?averageSalary=80000&firstDay=2024-10-11&lastDay=2024-10-01")).andExpect(model().attribute("exception", "Дата окончания отпуска раньше даты начала."));
    }    
}