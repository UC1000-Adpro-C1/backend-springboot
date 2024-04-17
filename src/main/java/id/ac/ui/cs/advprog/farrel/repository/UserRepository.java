package id.ac.ui.cs.advprog.farrel.repository;

import java.util.ArrayList;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.model.User;

public class UserRepository {
    private ArrayList<User> listAllUser= new ArrayList<>();
    
    public void updateTopUpStatus(TopUp updatedTopUp) {
        for (User tempUser: listAllUser) {
            for(TopUp tempTopUp: tempUser.getUserTopUpList()){
                if (tempTopUp.getTopUpId().equals(updatedTopUp.getTopUpId()))
                if (tempTopUp.getStatus().equals("DITERIMA")){
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
}
