package org.betfair.christmas_logistic.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.betfair.christmas_logistic.controller.dto.OrderCreateDto;
import org.betfair.christmas_logistic.controller.dto.OrderDto;
import org.betfair.christmas_logistic.controller.exception.InvalidOrderCreateDtoException;
import org.betfair.christmas_logistic.converter.OrderConverter;
import org.betfair.christmas_logistic.dao.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.betfair.christmas_logistic.converter.OrderConverter.entityListToDtoList;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;
    private final CompanyInfo companyInfo;

    public void cancelOrders(List<Long> orderIds) {
        List<Order> ordersToCancel = orderRepository.findAllById(orderIds);
        for (Order order : ordersToCancel) {
            orderRepository.changeOrderStatus(order, OrderStatus.CANCELED);
        }
        orderRepository.saveAll(ordersToCancel);
    }

    public List<OrderDto> findOrders(String deliveryDate, String destinationName) {
        if (deliveryDate == null || deliveryDate.isBlank()) {
            deliveryDate = companyInfo.getCurrentDateString();
        }
        if (destinationName == null || destinationName.isBlank()) {
            destinationName = "";
        }

        List<Order> foundOrders = orderRepository.findAllByDestination_NameContainingIgnoreCaseAndDeliveryDate(destinationName, deliveryDate);
        return entityListToDtoList(foundOrders);
    }

    @Transactional
    public List<OrderDto> createOrders(List<OrderCreateDto> ordersToCreate) throws InvalidOrderCreateDtoException {

        List<String> destinationNames = ordersToCreate.stream()
                .map(OrderCreateDto::destinationName)
                .distinct()
                .toList();

        Map<String, Destination> destinationsByName = destinationRepository.findAllByNameIn(destinationNames).stream()
                .collect(Collectors.toMap(Destination::getName, Function.identity()));

        validateOrderList(ordersToCreate, destinationsByName);

        List<Order> ordersToSave = ordersToCreate.stream()
                .map(orderCreateDto -> buildNewOrder(orderCreateDto, destinationsByName))
                .toList();

        List<Order> savedOrders = orderRepository.saveAll(ordersToSave);

        return OrderConverter.entityListToDtoList(savedOrders);
    }

    private void validateOrderList(List<OrderCreateDto> ordersToCreate, Map<String, Destination> destinationsByName) throws InvalidOrderCreateDtoException {
        LocalDate currentDate = companyInfo.getCurrentDate();
        for (OrderCreateDto orderCreateDto : ordersToCreate) {
            if (!destinationsByName.containsKey(orderCreateDto.destinationName())) {
                throw new InvalidOrderCreateDtoException("Destination name is invalid: " + orderCreateDto.destinationName());
            }
            LocalDate orderDate = LocalDate.parse(orderCreateDto.deliveryDate(), CompanyInfo.dateTimeFormatter);
            if (!currentDate.isBefore(orderDate)) {
                throw new InvalidOrderCreateDtoException("Delivery date should ve after: " + currentDate);
            }
        }
    }

    private static Order buildNewOrder(OrderCreateDto orderCreateDto,
                                       Map<String, Destination> destinationsByName) {
        return Order.builder()
                .deliveryDate(orderCreateDto.deliveryDate())
                .destination(destinationsByName.get(orderCreateDto.destinationName()))
                .status(OrderStatus.NEW)
                .lastUpdated(System.currentTimeMillis())
                .build();
    }

    public List<Order> getOrdersForDate(LocalDate currentDate) {
        String dateAsString = CompanyInfo.dateTimeFormatter.format(currentDate);
        return orderRepository.findAllByDeliveryDate(dateAsString);
    }

    @SneakyThrows
    @Async("deliveryExecutor")
    public void startDeliveringOrders(Destination destination, List<Order> orders) {
        List<Long> orderIds = orders.stream()
                .map(Order::getId)
                .toList();

        log.info("Starting deliveries to {} on {} for {} km.", destination.getName(), Thread.currentThread().getName(), destination.getDistance());
        orderRepository.markAsDelivering(orderIds);

        Thread.sleep(destination.getDistance() * 1000);

        int deliveredCount = orderRepository.markAsDelivered(orderIds);
        companyInfo.increaseProfit((long) deliveredCount * destination.getDistance());

        log.info("Delivered to {}", destination.getName());
    }
}
