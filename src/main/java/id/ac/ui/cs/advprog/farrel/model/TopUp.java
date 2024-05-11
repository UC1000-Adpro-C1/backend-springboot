package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.strategy.TopUpSortingStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Table(name="topup")
@Entity
@Getter @Setter
public class TopUp {

    @Column(name="amount")
    private long amount;

    @Column(name = "status")
    private String status = TopUpStatus.PENDING.name();

    @Column(name = "transactionTime")
    private LocalDate transactionTime;

//    private ArrayList<User> observerUsers = new ArrayList<User>();

    @Id
    @Column(name = "topUpId", updatable = false, nullable = false)
    private UUID topUpId;

    @Column(name = "userOwnerId")
    private String userOwnerId;

    @Setter
    transient private TopUpSortingStrategy sortingStrategy;

    public TopUp(UUID topUpId, ArrayList<User> observerUsers, long amount, LocalDate transactionTime, String userOwnerId) {
        this.topUpId = topUpId;
        this.transactionTime = transactionTime;
        this.status = TopUpStatus.PENDING.getValue();
        this.userOwnerId = userOwnerId;

        if (observerUsers.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
//            this.observerUsers = observerUsers;
        }
    }

    public void setStatus(String status) {
        if (TopUpStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}