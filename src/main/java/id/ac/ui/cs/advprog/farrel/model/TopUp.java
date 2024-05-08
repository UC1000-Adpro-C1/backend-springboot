package id.ac.ui.cs.advprog.farrel.model;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TopUp {
    private long amount;
    private String status = TopUpStatus.PENDING.name();
    private LocalDate transactionTime;
    private ArrayList<User> observerUsers = new ArrayList<User>();
    private UUID topUpId;
    private String userOwnerId;

    public TopUp () {};

    public TopUp (UUID topUpId, ArrayList<User> observerUsers, long amount, LocalDate transactionTime, String userOwnerId) {
        this.topUpId = topUpId;
        this.transactionTime = transactionTime;
        this.status = TopUpStatus.PENDING.getValue();
        this.userOwnerId = userOwnerId;

        if (observerUsers.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.observerUsers = observerUsers;
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