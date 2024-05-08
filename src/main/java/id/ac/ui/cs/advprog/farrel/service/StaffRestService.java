package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffRestService {

    private final TopUpIterator topUpIterator;

    @Autowired
    public StaffRestService(TopUpIterator topUpIterator) {
        this.topUpIterator = topUpIterator;
    }

    public TopUp updateTopUpStatus(UUID id, TopUpStatus status, Staff staff) {
        TopUp topUp = topUpIterator.findById(id);
        if (topUp == null) {
            throw new IllegalArgumentException("TopUp not found: " + id);
        }

        if (!staff.isStaff()) {
            throw new IllegalArgumentException("Unauthorized access");
        }

        if (!topUp.getStatus().equals(TopUpStatus.PENDING.name())) {
            throw new IllegalArgumentException("TopUp status is not PENDING");
        }

        topUp.setStatus(status.name());
        topUpIterator.update(topUp);
        return topUp;
    }

    public List<TopUp> getPendingTopUps() {
        return topUpIterator.findByStatus(TopUpStatus.PENDING.name());
    }

    public List<TopUp> getNonPendingTopUps() {
        return topUpIterator.findNotByStatus(TopUpStatus.PENDING.name());
    }
}