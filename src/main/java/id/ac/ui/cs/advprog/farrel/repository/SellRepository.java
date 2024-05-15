package id.ac.ui.cs.advprog.farrel.repository;
import id.ac.ui.cs.advprog.farrel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Product, String> {
    List<Product> findBySellerId(String id);
}