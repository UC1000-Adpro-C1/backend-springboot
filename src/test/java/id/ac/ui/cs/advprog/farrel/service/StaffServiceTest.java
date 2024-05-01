package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import id.ac.ui.cs.advprog.farrel.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class StaffServiceTest {
    private StaffRepository staffRepository;
    private TopUpIterator topUpIterator;
    private StaffService staffService;

    @BeforeEach
    public void setUp() {
        staffRepository = mock(StaffRepository.class);
        topUpIterator = mock(TopUpIterator.class);
        staffService = new StaffService(staffRepository, topUpIterator);
    }

    @Test
    public void testApproveTopUp() {
        Staff staff = new Staff();
        staff.setUserId("staff1");
        staff.setStaff(true);
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);

        when(staffRepository.findByUserId("staff1")).thenReturn(staff);
        when(topUpIterator.findById(topUpId)).thenReturn(topUp);

        staffService.approveTopUp("staff1", topUpId);

        verify(staffRepository, times(1)).findByUserId("staff1");
        verify(topUpIterator, times(1)).findById(topUpId);
        verify(topUpIterator, times(1)).save(topUp);

        assert topUp.getStatus().equals(TopUpStatus.SUCCESS.name());
    }

    @Test
    public void testRejectTopUp() {
        Staff staff = new Staff();
        staff.setUserId("staff1");
        staff.setStaff(true);
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);

        when(staffRepository.findByUserId("staff1")).thenReturn(staff);
        when(topUpIterator.findById(topUpId)).thenReturn(topUp);

        staffService.rejectTopUp("staff1", topUpId);

        verify(staffRepository, times(1)).findByUserId("staff1");
        verify(topUpIterator, times(1)).findById(topUpId);
        verify(topUpIterator, times(1)).save(topUp);

        assert topUp.getStatus().equals(TopUpStatus.FAILED.name());
    }

    @Test
    public void testApproveTopUpNonStaff() {
        Staff staff = new Staff();
        staff.setUserId("buyer1");
        staff.setStaff(false);
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);

        when(staffRepository.findByUserId("buyer1")).thenReturn(staff);
        when(topUpIterator.findById(topUpId)).thenReturn(topUp);

        staffService.approveTopUp("buyer1", topUpId);

        verify(staffRepository, times(1)).findByUserId("buyer1");
        verify(topUpIterator, never()).findById(topUpId);
        verify(topUpIterator, never()).save(topUp);

        assert topUp.getStatus().equals(TopUpStatus.PENDING.name());
    }

    @Test
    public void testRejectTopUpNonStaff() {
        Staff staff = new Staff();
        staff.setUserId("buyer1");
        staff.setStaff(false);
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);

        when(staffRepository.findByUserId("buyer1")).thenReturn(staff);
        when(topUpIterator.findById(topUpId)).thenReturn(topUp);

        staffService.rejectTopUp("buyer1", topUpId);

        verify(staffRepository, times(1)).findByUserId("buyer1");
        verify(topUpIterator, never()).findById(topUpId);
        verify(topUpIterator, never()).save(topUp);

        assert topUp.getStatus().equals(TopUpStatus.PENDING.name());
    }
}
