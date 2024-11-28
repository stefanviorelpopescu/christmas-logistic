package org.betfair.christmas_logistic.config;

import lombok.RequiredArgsConstructor;
import org.betfair.christmas_logistic.dao.CompanyInfo;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomInfoContributor implements InfoContributor {

    private final CompanyInfo companyInfo;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("currentDate", companyInfo.getCurrentDateString());
        builder.withDetail("profit", companyInfo.getProfit());
    }
}
