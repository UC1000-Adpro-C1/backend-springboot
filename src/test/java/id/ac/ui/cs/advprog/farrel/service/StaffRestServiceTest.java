package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class StaffRestServiceTest {

    private StaffRestService staffRestService;

    @Mock
    private StaffRepository staffRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        staffRestService = new StaffRestService(staffRepository);
    }

    @Test
    public void testFindByUserIdFound() {
        Staff staff = new Staff();
        staff.setUserId("staff1");
        staff.setName("John Doe");
        staff.setStaff(true);
        when(staffRepository.findByUserId("staff1")).thenReturn(staff);

        Staff result = staffRestService.findByUserId("staff1");

        assertEquals(staff, result);
        verify(staffRepository, times(1)).findByUserId("staff1");
    }

    @Test
    public void testFindByUserIdNotFound() {
        when(staffRepository.findByUserId("staff1")).thenReturn(null);

        Staff result = staffRestService.findByUserId("staff1");

        assertNull(result);
        verify(staffRepository, times(1)).findByUserId("staff1");
    }

    @Test
    public void testSave() {
        Staff staff = new Staff();
        staff.setUserId("staff1");
        staff.setName("John Doe");
        staff.setStaff(true);

        staffRestService.save(staff);

        verify(staffRepository, times(1)).save(staff);
    }
}
