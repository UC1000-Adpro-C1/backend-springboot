package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffRestService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffRestService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff findByUserId(String userId) {
        return staffRepository.findByUserId(userId);
    }

    public void save(Staff staff) {
        staffRepository.save(staff);
    }
}