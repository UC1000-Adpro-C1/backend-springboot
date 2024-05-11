package id.ac.ui.cs.advprog.farrel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
@Repository
public interface StaffTopUpRepository extends JpaRepository<TopUp, String> {
}