package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.StaffRepository;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StaffRestServiceTest {

    private StaffRestService staffRestService;

    @Mock
    private TopUpIterator topUpIteratorMock;

    @Mock
    private StaffRepository staffRepositoryMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        staffRestService = new StaffRestService(topUpIteratorMock, staffRepositoryMock);
    }

    @Test
    public void testGetTopUpHistory() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setTopUpId(UUID.randomUUID());
        topUp1.setStatus(TopUpStatus.PENDING.name());
        TopUp topUp2 = new TopUp();
        topUp2.setTopUpId(UUID.randomUUID());
        topUp2.setStatus(TopUpStatus.SUCCESS.name());
        topUps.add(topUp1);
        topUps.add(topUp2);

        when(topUpIteratorMock.findNotByStatus(TopUpStatus.PENDING.name())).thenReturn(topUps);

        List<TopUp> result = staffRestService.getTopUpHistory();

        assertEquals(1, result.size());
        assertEquals(TopUpStatus.SUCCESS.name(), result.get(0).getStatus());
    }

    @Test
    public void testGetOnGoingTopUp() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setTopUpId(UUID.randomUUID());
        topUp1.setStatus("PENDING");
        TopUp topUp2 = new TopUp();
        topUp2.setTopUpId(UUID.randomUUID());
        topUp2.setStatus("SUCCESS");
        topUps.add(topUp1);
        topUps.add(topUp2);

        when(topUpIteratorMock.findByStatus("PENDING")).thenReturn(topUps);

        List<TopUp> result = staffRestService.getOnGoingTopUp();

        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
    }

    @Test
    public void testCheckIsStaff() {
        Staff staff = new Staff();
        staff.setUserId("staffId");
        when(staffRepositoryMock.findByUserId("staffId")).thenReturn(staff);

        boolean result = staffRestService.checkIsStaff("staffId");

        assertEquals(true, result);
    }

    @Test
    public void testUpdateTopUpStatus() {
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);
        topUp.setStatus("PENDING");
        when(topUpIteratorMock.findById(topUpId)).thenReturn(topUp);

        staffRestService.updateTopUpStatus("staffId", topUpId, TopUpStatus.SUCCESS.name());

        assertEquals("SUCCESS", topUp.getStatus());
        verify(topUpIteratorMock, times(1)).save(topUp);
    }
}
