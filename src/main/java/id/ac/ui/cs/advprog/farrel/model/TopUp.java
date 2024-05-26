package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.strategy.TopUpSortingStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import id.ac.ui.cs.advprog.farrel.topupstate.PendingState;
import id.ac.ui.cs.advprog.farrel.topupstate.SuccessState;
import id.ac.ui.cs.advprog.farrel.topupstate.FailedState;
import id.ac.ui.cs.advprog.farrel.topupstate.TopUpState;
import jakarta.persistence.*;
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

    @Transient
    private TopUpState currentState = new PendingState();

    @Column(name="amount")
    private long amount;

    @Column(name = "status")
    private String status = currentState.getState();

    @Column(name = "transactionTime")
    private LocalDate transactionTime;

//    private ArrayList<User> observerUsers = new ArrayList<User>();

    @Id
    @Column(name = "topUpId", updatable = false, nullable = false)
    private UUID topUpId;

    @Column(name = "userOwnerId", updatable = false, nullable = false)
    private String userOwnerId;

    public TopUp(UUID topUpId, ArrayList<User> observerUsers, long amount, LocalDate transactionTime, String userOwnerId) {
        this.topUpId = topUpId;
        this.transactionTime = transactionTime;
        this.status = new PendingState().getState();
        this.userOwnerId = userOwnerId;

        if (observerUsers.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
//            this.observerUsers = observerUsers;
        }
    }

    public void setStatus(String status) {
        if (TopUpStatus.contains(status)) {
            if (status.equals(TopUpStatus.FAILED.getValue())) {
                currentState.processFailed(this);
                this.status = currentState.getState();
            } else if (status.equals(TopUpStatus.SUCCESS.getValue())) {
                currentState.processSuccess(this);
                this.status = currentState.getState();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}