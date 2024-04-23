package id.ac.ui.cs.advprog.farrel.util;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TopUpIteratorTest {
    private List<TopUp> topUps;
    private TopUpIterator topUpIterator;

    @BeforeEach
    public void setUp() {
        topUps = new ArrayList<TopUp>();
        TopUp topUp1 = new TopUp();
        topUp1.setTopUpId(UUID.randomUUID());
        topUp1.setAmount(100000);
        topUp1.setStatus(TopUp.Status.PENDING.name());
        topUps.add(topUp1);

        TopUp topUp2 = new TopUp();
        topUp2.setTopUpId(UUID.randomUUID());
        topUp2.setAmount(200000);
        topUp2.setStatus(TopUp.Status.SUCCESS.name());
        topUps.add(topUp2);

        TopUp topUp3 = new TopUp();
        topUp3.setTopUpId(UUID.randomUUID());
        topUp3.setAmount(300000);
        topUp3.setStatus(TopUp.Status.PENDING.name());
        topUps.add(topUp3);

        topUpIterator = new TopUpIterator(topUps);
    }

    @Test
    public void testHasNext() {
        assertTrue(topUpIterator.hasNext());
        topUpIterator.next();
        assertTrue(topUpIterator.hasNext());
        topUpIterator.next();
        assertTrue(topUpIterator.hasNext());
        topUpIterator.next();
        assertFalse(topUpIterator.hasNext());
    }

    @Test
    public void testNext() {
        TopUp topUp1 = topUpIterator.next();
        assertEquals(100000, topUp1.getAmount());
        assertEquals(TopUp.Status.PENDING.name(), topUp1.getStatus());

        TopUp topUp2 = topUpIterator.next();
        assertEquals(200000, topUp2.getAmount());
        assertEquals(TopUp.Status.SUCCESS.name(), topUp2.getStatus());

        TopUp topUp3 = topUpIterator.next();
        assertEquals(300000, topUp3.getAmount());
        assertEquals(TopUp.Status.PENDING.name(), topUp3.getStatus());

        assertNull(topUpIterator.next());
    }

    @Test
    public void testFindByStatusPending() {
        ArrayList<TopUp> pendingTopUps = (ArrayList<TopUp>) topUpIterator.findByStatus("PENDING");
        assertEquals(2, pendingTopUps.size());
        assertEquals(TopUp.Status.PENDING.name(), pendingTopUps.get(0).getStatus());
        assertEquals(TopUp.Status.PENDING.name(), pendingTopUps.get(1).getStatus());
    }

    @Test
    public void testFindByStatusNotPending() {
        ArrayList<TopUp> nonPendingTopUps = (ArrayList<TopUp>) topUpIterator.findNotByStatus("PENDING");
        assertEquals(1, nonPendingTopUps.size());
        assertEquals("SUCCESS", nonPendingTopUps.get(0).getStatus());
    }

    @Test
    public void testFindByIdFound() {
        UUID id = topUps.get(0).getTopUpId();
        TopUp foundTopUp = (TopUp) topUpIterator.findById(id);
        assertNotNull(foundTopUp);
        assertNotNull(foundTopUp);
        assertEquals(id, foundTopUp.getTopUpId());
    }

    @Test
    public void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        TopUp foundTopUp = (TopUp) topUpIterator.findById(id);
        assertNull(foundTopUp);
    }
}