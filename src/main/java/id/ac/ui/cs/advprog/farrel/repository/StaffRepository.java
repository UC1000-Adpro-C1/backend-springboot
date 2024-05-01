package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.model.Staff;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StaffRepository {
    private Map<String, Staff> staffMap = new HashMap<>();

    public void save(Staff staff) {
        staffMap.put(staff.getUserId(), staff);
    }

    public Staff findByUserId(String username) {
        return staffMap.get(username);
    }
}
