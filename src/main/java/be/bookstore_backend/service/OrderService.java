package be.bookstore_backend.service;

import be.bookstore_backend.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> getUserOrderHistory(String username);
}
