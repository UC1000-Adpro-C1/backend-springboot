package id.ac.ui.cs.advprog.farrel.model;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TopUp {
    private long amount;
    private enum Status {
        PENDING,
        DIBATALKAN,
        DITERIMA,
        DITOLAK
    }
    private String status = Status.PENDING.name();
    private LocalDate transactionTime;
    private ArrayList<User> observerUser = new ArrayList<>();
    private String topUpId;
}