package be.bookstore_backend.controller;

import be.bookstore_backend.model.Order;
import be.bookstore_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrderHistory(Authentication authentication) {
        List<Order> orders = orderService.getUserOrderHistory(authentication.getName());
        return ResponseEntity.ok(orders);
    }
}
