package org.betfair.christmas_logistic.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betfair.christmas_logistic.dao.CompanyInfo;
import org.betfair.christmas_logistic.dao.Destination;
import org.betfair.christmas_logistic.dao.Order;
import org.betfair.christmas_logistic.service.OrderService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingFacade {

    private final CompanyInfo companyInfo;
    private final OrderService orderService;
    private final TaskExecutor taskExecutor2;

    public void newDay() {
        companyInfo.advanceCurrentDate();
        LocalDate currentDate = companyInfo.getCurrentDate();
        log.info("New day starting: {}", currentDate);

        Map<Destination, List<Order>> ordersByDestination = orderService.getOrdersForDate(currentDate).stream()
                .collect(Collectors.groupingBy(Order::getDestination));

        String destinationList = ordersByDestination.keySet().stream()
                .map(Destination::getName)
                .collect(Collectors.joining(", "));
        log.info("Today we will be delivering to {}", destinationList);

        ordersByDestination.entrySet().forEach(destinationAndOrders ->
                taskExecutor2.execute(new DestinationTask(destinationAndOrders)));

    }
}
