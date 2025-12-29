package romanryskov.shoelastshop.repository;

import romanryskov.shoelastshop.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
