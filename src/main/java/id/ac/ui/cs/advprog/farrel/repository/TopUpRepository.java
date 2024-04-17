package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;


@Repository
public class TopUpRepository {
    private ArrayList<TopUp> listAllTopUp = new ArrayList<>();

    public TopUp create(TopUp topUp) {
        listAllTopUp.add(topUp);
        return topUp;
    }

    public Iterator<TopUp> findAllTopUp() {
        return listAllTopUp.iterator();
    }

    public void updateTopUp(String topUpId, TopUp updatedTopUp) {
        for (int i = 0; i < listAllTopUp.size(); i++) {
            TopUp tempTopUp = listAllTopUp.get(i);
            if (tempTopUp.getTopUpId().equals(topUpId)) {
                tempTopUp.setStatus(updatedTopUp.getStatus());
            }
        }
    }
}
