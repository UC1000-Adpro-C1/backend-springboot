package id.ac.ui.cs.advprog.farrel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaffTopUpRepository extends JpaRepository<TopUp, UUID> {
    @Query("SELECT t FROM TopUp t WHERE t.status = :status")
    List<TopUp> findByStatus(@Param("status") String status);

    @Query("SELECT t FROM TopUp t WHERE t.status <> :status")
    List<TopUp> findByStatusNot(@Param("status") String status);
}