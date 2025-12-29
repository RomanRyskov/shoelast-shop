package romanryskov.shoelastshop.repository;

import romanryskov.shoelastshop.model.entity.Product;
import romanryskov.shoelastshop.model.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByProductType(ProductType type);
}