package id.ac.ui.cs.advprog.farrel.repository;
import id.ac.ui.cs.advprog.farrel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRepository extends JpaRepository<Product, String> {
}