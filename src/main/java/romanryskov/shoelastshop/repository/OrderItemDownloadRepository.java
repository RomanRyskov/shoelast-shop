package romanryskov.shoelastshop.repository;

import romanryskov.shoelastshop.model.entity.OrderItemDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDownloadRepository extends JpaRepository<OrderItemDownload, Integer> {
    OrderItemDownload findByDownloadToken(String downloadToken);
}
