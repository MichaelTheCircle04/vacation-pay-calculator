package com.mtrifonov.vacationpaycalculator.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @Mikhail Trifonov
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationsDTO {
    private Integer totalAmount;
    private List<Holiday> holidays;
}
