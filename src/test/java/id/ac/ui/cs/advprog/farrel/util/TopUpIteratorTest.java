package id.ac.ui.cs.advprog.farrel.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;

class TopUpIteratorTest {
    private List<TopUp> topUps;
    private TopUpIterator topUpIterator;

    @BeforeEach
    public void setUp() {
        topUps = new ArrayList<TopUp>();
        topUps.add(new TopUp(UUID.randomUUID(), "PENDING"));
        topUps.add(new TopUp(UUID.randomUUID(), "SUCCESS"));
        topUps.add(new TopUp(UUID.randomUUID(), "PENDING"));
        topUpIterator = new TopUpIterator(topUps);
    }

    @Test
    public void testFindByStatusPending() {
        ArrayList<TopUp> pendingTopUps = topUpIterator.findByStatus("PENDING");
        assertEquals(2, pendingTopUps.size());
        assertEquals("PENDING", pendingTopUps.get(0).getStatus());
        assertEquals("PENDING", pendingTopUps.get(1).getStatus());
    }

    @Test
    public void testFindByStatusNotPending() {
        ArrayList<TopUp> nonPendingTopUps = topUpIterator.findNotByStatus("PENDING");
        assertEquals(1, nonPendingTopUps.size());
        assertEquals("SUCCESS", nonPendingTopUps.get(0).getStatus());
    }

    @Test
    public void testFindByIdFound() {
        UUID id = topUps.get(0).getId();
        TopUp foundTopUp = (TopUp) topUpIterator.findById(id);
        assertNotNull(foundTopUp);
        assertNotNull(foundTopUp);
        assertEquals(id, foundTopUp.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        TopUp foundTopUp = (TopUp) topUpIterator.findById(id);
        assertNull(foundTopUp);
    }
}