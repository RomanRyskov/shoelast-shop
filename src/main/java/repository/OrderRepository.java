package repository;

import model.entity.Order;
import model.entity.User;
import model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    //для фильтрации по статусу заказа
    List<Order> findByStatus(OrderStatus status);
}
