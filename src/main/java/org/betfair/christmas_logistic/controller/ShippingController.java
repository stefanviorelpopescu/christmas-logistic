package org.betfair.christmas_logistic.controller;

import lombok.RequiredArgsConstructor;
import org.betfair.christmas_logistic.facade.ShippingFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingFacade shippingFacade;

    @PostMapping("/new-day")
    public void newDay() {
        shippingFacade.newDay();
    }

}
