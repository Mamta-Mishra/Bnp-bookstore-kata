package be.bookstore_backend.repository;

import be.bookstore_backend.model.Order;
import be.bookstore_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
}
