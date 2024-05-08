package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StaffRestServiceTest {

    private TopUpIterator topUpIterator;
    private StaffRestService staffRestService;

    @BeforeEach
    void setUp() {
        topUpIterator = new TopUpIterator(new ArrayList<>());
        staffRestService = new StaffRestService(topUpIterator);
    }

    @Test
    void testUpdateTopUpStatusHappy() {
        // Setup
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);
        topUp.setStatus(TopUpStatus.PENDING.name());
        Staff staff = new Staff();
        staff.setStaff(true);
        topUpIterator.save(topUp);

        // Test
        TopUp updatedTopUp = staffRestService.updateTopUpStatus(topUpId, TopUpStatus.SUCCESS, staff);

        // Verify
        assertEquals(TopUpStatus.SUCCESS.name(), updatedTopUp.getStatus());
    }

    @Test
    void testUpdateTopUpStatusUnhappyTopUpNotFound() {
        // Setup
        UUID nonExistentTopUpId = UUID.randomUUID();
        Staff staff = new Staff();
        staff.setStaff(true);

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> staffRestService.updateTopUpStatus(nonExistentTopUpId, TopUpStatus.SUCCESS, staff));
    }

    @Test
    void testGetPendingTopUpsHappy() {
        // Setup
        TopUp topUp1 = new TopUp();
        topUp1.setStatus(TopUpStatus.PENDING.name());
        TopUp topUp2 = new TopUp();
        topUp2.setStatus(TopUpStatus.PENDING.name());
        TopUp topUp3 = new TopUp();
        topUp3.setStatus(TopUpStatus.SUCCESS.name()); // Non-pending status
        topUpIterator.save(topUp1);
        topUpIterator.save(topUp2);
        topUpIterator.save(topUp3);

        // Test
        List<TopUp> pendingTopUps = staffRestService.getPendingTopUps();

        // Verify
        assertEquals(2, pendingTopUps.size());
    }

    @Test
    void testUpdateTopUpStatusUnhappyInvalidStatus() {
        // Setup
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);
        topUp.setStatus(TopUpStatus.SUCCESS.name()); // Existing status is not PENDING
        Staff staff = new Staff();
        staff.setStaff(true);
        topUpIterator.save(topUp);

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> staffRestService.updateTopUpStatus(topUpId, TopUpStatus.SUCCESS, staff));
    }

    @Test
    void testUpdateTopUpStatusUnhappyUnauthorizedAccess() {
        // Setup
        UUID topUpId = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(topUpId);
        topUp.setStatus(TopUpStatus.PENDING.name());
        Staff staff = new Staff(); // Not a valid staff
        staff.setStaff(false);
        topUpIterator.save(topUp);

        // Test & Verify
        assertThrows(IllegalArgumentException.class, () -> staffRestService.updateTopUpStatus(topUpId, TopUpStatus.SUCCESS, staff));
    }


}