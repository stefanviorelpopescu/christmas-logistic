package org.betfair.christmas_logistic.controller;

import lombok.RequiredArgsConstructor;
import org.betfair.christmas_logistic.controller.dto.OrderCreateDto;
import org.betfair.christmas_logistic.controller.dto.OrderDto;
import org.betfair.christmas_logistic.controller.exception.InvalidOrderCreateDtoException;
import org.betfair.christmas_logistic.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/status")
    public List<OrderDto> findOrders(@RequestParam(required = false) String date,
                                     @RequestParam(required = false) String destination) {
        return orderService.findOrders(date, destination);
    }

    @PostMapping("/cancel")
    public void cancelOrders(@RequestBody List<Long> orderIds) {
        orderService.cancelOrders(orderIds);
    }

    @PostMapping
    public List<OrderDto> addOrders(@RequestBody List<OrderCreateDto> ordersToCreate) throws InvalidOrderCreateDtoException {
        return orderService.createOrders(ordersToCreate);
    }
}
