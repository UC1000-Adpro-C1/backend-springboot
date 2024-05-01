package id.ac.ui.cs.advprog.farrel.service;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import id.ac.ui.cs.advprog.farrel.repository.StaffRepository;

import java.util.UUID;

@Service
public class StaffService {
    private StaffRepository staffRepository;
    private TopUpIterator topUpIterator;

    public StaffService(StaffRepository staffRepository, TopUpIterator topUpIterator) {
        this.staffRepository = staffRepository;
        this.topUpIterator = topUpIterator;
    }

    public void approveTopUp(String staffUsername, UUID topUpId) {
        Staff staff = staffRepository.findByUserId(staffUsername);

        if (staff != null) {
            if (staff.isStaff()) {
                TopUp topUp = topUpIterator.findById(topUpId);

                if (topUp != null) {
                    topUp.setStatus(TopUpStatus.SUCCESS.name());
                    topUpIterator.save(topUp);
                }
            }
        }
    }

    public void rejectTopUp(String staffUsername, UUID topUpId) {
        Staff staff = staffRepository.findByUserId(staffUsername);

        if (staff != null) {
            if (staff.isStaff()) {
                TopUp topUp = topUpIterator.findById(topUpId);

                if (topUp != null) {
                    topUp.setStatus(TopUpStatus.FAILED.name());
                    topUpIterator.save(topUp);
                }
            }
        }
    }
}
