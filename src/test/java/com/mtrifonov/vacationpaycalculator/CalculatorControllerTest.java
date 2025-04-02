package com.mtrifonov.vacationpaycalculator;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mtrifonov.vacationpaycalculator.components.Calculator;
import com.mtrifonov.vacationpaycalculator.components.HolidayCalendar;
import com.mtrifonov.vacationpaycalculator.controllers.CalculatorController;

@WebMvcTest(controllers = CalculatorController.class)
@ContextConfiguration(classes = {HolidayCalendar.class, Calculator.class, VacationPayCalculator.class})
public class CalculatorControllerTest {

    MockMvc mvc;

    @BeforeEach
    void configMockMvc(WebApplicationContext ctx) {
        mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    void calculate_validRequest() throws Exception {

        var reqBuilder = 
            post("/calculate")
                .contentType(MediaType.APPLICATION_JSON);

        var content =
                    """
                    {
                        "averageSalary": "30000",
                        "numberOfDays": null,
                        "firstDay": "2025-02-01",
                        "lastDay": "2025-03-01"
                    }
                    """;

        mvc.perform(reqBuilder.content(content))
            .andExpectAll(
                status().isOk(), 
                jsonPath("$.totalAmount", is(27645)),
                jsonPath("$.holidays[0].nameHoliday",  is("День защитника Отечества"))
                );
        
        content =
                """
                {
                    "averageSalary": "30000",
                    "numberOfDays": 28,
                    "firstDay": null,
                    "lastDay": null
                }
                """;
        
        mvc.perform(reqBuilder.content(content))
                .andExpectAll(
                    status().isOk(), 
                    jsonPath("$.totalAmount", is(28668))
                    );

        content =
                """
                {
                    "averageSalary": "30000",
                    "numberOfDays": null,
                    "firstDay": "2025-04-28",
                    "lastDay": "2025-05-10"
                }
                """;
        
        mvc.perform(reqBuilder.content(content))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.holidays[0].nameHoliday",  is("Праздник Весны и Труда")),
                jsonPath("$.holidays[1].nameHoliday",  is("День Победы")), 
                jsonPath("$.totalAmount", is(10238))
                );
        
        content =
                """
                {
                    "averageSalary": "30000",
                    "numberOfDays": 12,
                    "firstDay": null,
                    "lastDay": null
                }
                """;

        mvc.perform(reqBuilder.content(content))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.totalAmount", is(12286))
                );
    }

    @Test
    void calculate_invalidBody() throws Exception {

        var reqBuilder = 
            post("/calculate")
                .contentType(MediaType.APPLICATION_JSON);

        //не указано кол-во дней и даты
        var content = 
                    """
                    {
                        "averageSalary": "30000",
                        "numberOfDays": null,
                        "firstDay": null,
                        "lastDay": null
                    }
                    """;

        mvc.perform(reqBuilder.content(content))
            .andExpectAll(
                status().isBadRequest(), 
                jsonPath("$.exception", is("Необходимо предоставить точные даты, либо колличество дней"))
                );

        //дата окончания раньше даты начала
        content = 
                """
                {
                    "averageSalary": "30000",
                    "numberOfDays": null,
                    "firstDay": "2025-05-10",
                    "lastDay": "2025-04-28"
                }
                """;

        mvc.perform(reqBuilder.content(content))
            .andExpectAll(
                status().isBadRequest(), 
                jsonPath("$.exception", is("Дата окончания отпуска раньше даты начала"))
                );

        //указана только одна дата
        content = 
                """
                {
                    "averageSalary": "30000",
                    "numberOfDays": null,
                    "firstDay": null,
                    "lastDay": "2025-05-10"
                }
                """;

        mvc.perform(reqBuilder.content(content))
            .andExpectAll(
                status().isBadRequest(), 
                jsonPath("$.exception", is("Нельзя предоставить только одну дату"))
                );
    }
}
