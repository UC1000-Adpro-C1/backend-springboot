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
    private ArrayList<User> observerUser = new ArrayList<User>();
    private UUID topUpId;
}