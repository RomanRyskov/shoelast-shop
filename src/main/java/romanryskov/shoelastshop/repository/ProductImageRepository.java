package romanryskov.shoelastshop.repository;

import romanryskov.shoelastshop.model.entity.Product;
import romanryskov.shoelastshop.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProduct(Product product);
}