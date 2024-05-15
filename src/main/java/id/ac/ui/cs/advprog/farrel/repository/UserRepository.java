package id.ac.ui.cs.advprog.farrel.repository;

import java.util.ArrayList;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private ArrayList<User> listAllUser = new ArrayList<>();

    public void updateTopUpStatus(TopUp updatedTopUp) {

        // ganti code dengan pengecekan pada top up apakah user terdapat pada
        for (User tempUser : listAllUser) {
            for (TopUp tempTopUp : tempUser.getUserTopUpList()) {
                if (tempTopUp.getTopUpId().equals(updatedTopUp.getTopUpId()))
                    if (tempTopUp.getStatus().equals("DITERIMA")) {
                        //implement penambahan uang user
                    }
                displayTopUpNotification(tempTopUp);
            }
        }
    }

    public void displayTopUpNotification(TopUp updatedTopUp) {
        System.out.println("Top Up dengan id "
                + updatedTopUp.getTopUpId() + " kini berstatus " + updatedTopUp.getStatus());
    }

    public void addBalance(TopUp successTopUp) {
        for (User tempUser : listAllUser) {
            for (TopUp tempTopUp : tempUser.getUserTopUpList()) {
                if (tempTopUp.getTopUpId().equals(successTopUp.getTopUpId())) {
                    tempUser.setBalance(tempUser.getBalance() + successTopUp.getAmount());
                }
            }
        }
    }
}
