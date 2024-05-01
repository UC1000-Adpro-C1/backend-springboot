package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.StaffRepository;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffRestService {

    private final TopUpIterator topUpIterator;
    private final StaffRepository staffRepository;

    public StaffRestService(TopUpIterator topUpIterator, StaffRepository staffRepository) {
        this.topUpIterator = topUpIterator;
        this.staffRepository = staffRepository;
    }

    public List<TopUp> getTopUpHistory() {
        return topUpIterator.findNotByStatus("PENDING");
    }

    public List<TopUp> getOnGoingTopUp() {
        return topUpIterator.findByStatus("PENDING");
    }

    public boolean checkIsStaff(String staffUsername) {
        Staff staff = staffRepository.findByUserId(staffUsername);

        return staff != null;
    }

    public void updateTopUpStatus(String staffUsername, UUID topUpId, String status) {
        if (checkIsStaff(staffUsername)) {
            TopUp topUp = topUpIterator.findById(topUpId);

            if (topUp != null) {
                if (status.equals(TopUpStatus.SUCCESS.name()))
                    topUp.setStatus(TopUpStatus.SUCCESS.name());
                else topUp.setStatus(TopUpStatus.FAILED.name());

                topUpIterator.save(topUp);
            }
        }
    }
}