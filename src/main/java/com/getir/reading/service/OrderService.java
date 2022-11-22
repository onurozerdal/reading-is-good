package com.getir.reading.service;

import com.getir.reading.common.Utils;
import com.getir.reading.exception.ExceptionFactory;
import com.getir.reading.model.Book;
import com.getir.reading.model.OrderLine;
import com.getir.reading.model.Orders;
import com.getir.reading.model.request.OrderLineRequest;
import com.getir.reading.model.request.OrderListRequest;
import com.getir.reading.model.request.OrderRequest;
import com.getir.reading.model.response.OrderLines;
import com.getir.reading.model.response.OrderResponse;
import com.getir.reading.repository.OrderCustomRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderCustomRepository orderCustomRepository;

    private final BookService bookService;

    @Autowired
    public OrderService(OrderCustomRepository orderCustomRepository, BookService bookService) {
        this.orderCustomRepository = orderCustomRepository;
        this.bookService = bookService;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        List<OrderLineRequest> orderLineRequest = request.getOrderLines();
        if (orderLineRequest == null || orderLineRequest.size() < 1) {
            ExceptionFactory.throwBadRequestException("There is no code and quantity in request.");
        }
        Orders orders = new Orders();
        long orderNumber = Utils.generateOrderNumber();
        orders.setOrderNumber(orderNumber);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("createOrder is called. calledBy: {}", email);
        orders.setEmail(email);

        Orders savedOrders = orderCustomRepository.saveOrUpdate(orders);
        List<OrderLines> orderLines = createOrderLine(savedOrders, orderLineRequest);
        double totalAmount = orderLines.stream().mapToDouble(OrderLines::getAmount).sum();
        orderCustomRepository.updateTotalAmount(orderNumber, totalAmount);

        OrderResponse response = new OrderResponse();
        response.setOrderNumber(orderNumber);
        response.setTotalAmount(totalAmount);
        response.setOrderLines(orderLines);
        return response;
    }

    private List<OrderLines> createOrderLine(Orders orders, List<OrderLineRequest> request) {
        logger.info("createOrderLine is called. calledBy: {}", orders.getEmail());
        List<OrderLines> responses = new ArrayList<>();
        for (OrderLineRequest orderLineRequest : request) {
            String code = orderLineRequest.getCode();
            if (StringUtils.isEmpty(code)) {
                ExceptionFactory.throwBadRequestException("Code should not be blank.");
            }
            Integer quantity = orderLineRequest.getQuantity();
            if (quantity == null || quantity < 1) {
                ExceptionFactory.throwBadRequestException(String.format("Quantity is not valid for %s", code));
            }
            Book book = bookService.getBook(code);
            if (book == null) {
                ExceptionFactory.throwNotFoundException(String.format("There is no recorded book with this code : %s.", code));
            }

            OrderLine orderLine = new OrderLine();
            orderLine.setOrderNumber(orders.getOrderNumber());
            orderLine.setCode(code);
            orderLine.setOrders(orders);
            double amount = quantity * book.getPrice();
            orderLine.setQuantity(quantity);
            orderLine.setAmount(amount);
            orderLine.setEmail(orders.getEmail());
            orderCustomRepository.saveOrUpdate(orderLine);

            bookService.updateStock(code, quantity);

            responses.add(new OrderLines(code, quantity, amount));
        }
        return responses;
    }

    public OrderResponse getByOrderNumber(Long orderNumber) {
        logger.info("getByOrderNumber is called. orderNumber: {}", orderNumber);
        if (orderNumber == null) {
            ExceptionFactory.throwBadRequestException("Order Number should not be blank.");
        }

        List<OrderLine> orderLines = orderCustomRepository.getOrderLines(orderNumber);
        Map<Orders, List<OrderLine>> orders = orderLines.stream().collect(Collectors.groupingBy(OrderLine::getOrders));
        List<OrderResponse> orderResponses = orders.keySet().stream().map(item -> new OrderResponse(item.getOrderNumber(), item.getTotalAmount(), orders.get(item).stream().map(orderLine -> new OrderLines(orderLine.getCode(), orderLine.getQuantity(), orderLine.getAmount())).toList())).toList();

        if (orderResponses.size() < 1) {
            ExceptionFactory.throwNotFoundException(String.format("There is no order with this order number %s", orderNumber));
        }
        return orderResponses.get(0);
    }

    public List<OrderResponse> list(OrderListRequest request) {
        if (request == null) {
            ExceptionFactory.throwBadRequestException("Start Date and End Date should not be blank.");
        }
        String startDate = request.getStartDate();
        if (StringUtils.isEmpty(startDate)) {
            ExceptionFactory.throwBadRequestException("Start Date should not be blank.");
        }
        String endDate = request.getEndDate();
        if (StringUtils.isEmpty(endDate)) {
            ExceptionFactory.throwBadRequestException("End Date should not be blank.");
        }
        logger.info("list is called. startDate: {}, endDate: {}", request.getStartDate(), request.getEndDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formatter.parse(startDate);
        } catch (ParseException e) {
            ExceptionFactory.throwBadRequestException("Start Date format is not valid.");
        }
        try {
            formatter.parse(endDate);
        } catch (ParseException e) {
            ExceptionFactory.throwBadRequestException("End Date format is not valid.");
        }
        List<OrderLine> orderLines = orderCustomRepository.list(startDate, endDate);
        return getOrderResponse(orderLines);
    }

    public List<OrderResponse> getCustomerOrders(String email, Integer page, Integer pageSize) {
        logger.info("getCustomerOrders is called. email: {}", email);
        if (StringUtils.isEmpty(email)) {
            ExceptionFactory.throwBadRequestException("Email should not be blank.");
        }
        if (page == null || page < 1) {
            ExceptionFactory.throwBadRequestException("Page is not valid.");
        }
        if (pageSize == null || pageSize < 1) {
            ExceptionFactory.throwBadRequestException("Page is not valid.");
        }
        List<OrderLine> orderLines = orderCustomRepository.getOrderLines(email, page, pageSize);
        return getOrderResponse(orderLines);
    }

    private List<OrderResponse> getOrderResponse(List<OrderLine> orderLines) {
        Map<Orders, List<OrderLine>> orders = orderLines.stream().collect(Collectors.groupingBy(OrderLine::getOrders));
        return orders.keySet().stream().map(item -> new OrderResponse(item.getOrderNumber(), item.getTotalAmount(), orders.get(item).stream().map(orderLine -> new OrderLines(orderLine.getCode(), orderLine.getQuantity(), orderLine.getAmount())).toList())).toList();
    }
}
