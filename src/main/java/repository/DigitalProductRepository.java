package repository;

import model.entity.DigitalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalProductRepository extends JpaRepository<DigitalProduct, Long> {

}
