package id.ac.ui.cs.advprog.farrel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.farrel.model.Review;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.List;

@Repository
public interface ReviewDb extends JpaRepository<Review, String> {
    List<Review> findByProductId(String productId);
}
