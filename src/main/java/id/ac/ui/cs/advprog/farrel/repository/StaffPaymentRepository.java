package id.ac.ui.cs.advprog.farrel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.farrel.model.Payment;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaffPaymentRepository extends JpaRepository<Payment, UUID> {
    @Query("SELECT t FROM Payment t WHERE t.status = :status")
    List<Payment> findByStatus(@Param("status") String status);

    @Query("SELECT t FROM Payment t WHERE t.status <> :status")
    List<Payment> findByStatusNot(@Param("status") String status);
}