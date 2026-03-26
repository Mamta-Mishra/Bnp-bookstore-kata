package be.bookstore_backend.service.impl;

import be.bookstore_backend.exception.ResourceNotFoundException;
import be.bookstore_backend.model.Order;
import be.bookstore_backend.model.User;
import be.bookstore_backend.repository.OrderRepository;
import be.bookstore_backend.repository.UserRepository;
import be.bookstore_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public List<Order> getUserOrderHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}
