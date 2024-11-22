package org.betfair.christmas_logistic.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Getter
@RequiredArgsConstructor
public class CompanyInfo {

    private final LocalDate currentDate = LocalDate.of(2021, 12, 15);
    private Long profit;

    public String getCurrentDateString() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(currentDate);
    }

}
