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

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private LocalDate currentDate = LocalDate.of(2021, 12, 14);
    private long profit;

    public String getCurrentDateString() {
        return dateTimeFormatter.format(currentDate);
    }

    public void advanceCurrentDate() {
        currentDate = currentDate.plusDays(1);
    }

    public synchronized void increaseProfit(long delta) {
        profit += delta;
    }

}
