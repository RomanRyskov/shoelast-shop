package romanryskov.shoelastshop.repository;

import romanryskov.shoelastshop.model.entity.Order;
import romanryskov.shoelastshop.model.entity.User;
import romanryskov.shoelastshop.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    //для фильтрации по статусу заказа
    List<Order> findByStatus(OrderStatus status);
}
