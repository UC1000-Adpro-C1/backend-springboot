package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StaffRepositoryTest {
    private StaffRepository staffRepository;

    @BeforeEach
    public void setUp() {
        staffRepository = new StaffRepository();
    }

    @Test
    public void testSaveAndFindByUsername() {
        Staff staff = new Staff();
        staff.setUserId("staff1");

        staffRepository.save(staff);

        Staff foundStaff = staffRepository.findByUserId("staff1");
        assertEquals(staff, foundStaff);
    }

    @Test
    public void testNotSaveAndFindByUsername() {
        Staff staff = new Staff();
        staff.setUserId("staff1");

        Staff foundStaff = staffRepository.findByUserId("staff1");
        assertNull(foundStaff);
    }

    @Test
    public void testFindByUsernameNonExistent() {
        Staff foundStaff = staffRepository.findByUserId("nonexistent");
        assertNull(foundStaff);
    }
}
