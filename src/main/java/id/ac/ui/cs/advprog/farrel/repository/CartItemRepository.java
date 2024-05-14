package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
}