package id.ac.ui.cs.advprog.farrel.util;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TopUpIterator {
    private final List<TopUp> topUps;
    private int position = 0;

    public TopUpIterator(List<TopUp> topUps) {
        this.topUps = topUps;
    }

    public boolean hasNext() {
        return position < topUps.size();
    }

    public TopUp next() {
        if (hasNext()) {
            return topUps.get(position++);
        }
        return null;
    }

    public List<TopUp> findByStatus(String status) {
        List<TopUp> result = new ArrayList<TopUp>();
        for (TopUp topUp : topUps) {
            if (topUp.getStatus().equals(status)) {
                result.add(topUp);
            }
        }
        return result;
    }

    public List<TopUp> findNotByStatus(String status) {
        List<TopUp> result = new ArrayList<TopUp>();
        for (TopUp topUp : topUps) {
            if (!topUp.getStatus().equals(status)) {
                result.add(topUp);
            }
        }
        return result;
    }

    public TopUp findById(UUID id) {
        for (TopUp topUp : topUps) {
            if (topUp.getTopUpId().equals(id)) {
                return topUp;
            }
        }
        return null;
    }
}
