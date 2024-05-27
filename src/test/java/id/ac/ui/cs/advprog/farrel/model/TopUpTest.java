package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.topupstate.PendingState;
import id.ac.ui.cs.advprog.farrel.topupstate.SuccessState;
import id.ac.ui.cs.advprog.farrel.topupstate.FailedState;
import id.ac.ui.cs.advprog.farrel.topupstate.TopUpState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TopUpTest {

    private TopUp topUp;

    @BeforeEach
    void setUp() {
        topUp = new TopUp();
        topUp.setTopUpId(UUID.randomUUID());
        topUp.setAmount(1000L);
        topUp.setTransactionTime(LocalDate.now());
        topUp.setUserOwnerId("user123");
    }

    @Test
    void testDefaultStateIsPending() {
        assertEquals(new PendingState().getState(), topUp.getCurrentState().getState());
        assertEquals(new PendingState().getState(), topUp.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        topUp.setStatus(TopUpStatus.SUCCESS.getValue());
        assertEquals(new SuccessState().getState(), topUp.getStatus());
    }

    @Test
    void testSetStatusFailed() {
        topUp.setStatus(TopUpStatus.FAILED.getValue());
        assertEquals(new FailedState().getState(), topUp.getStatus());
    }

    @Test
    void testSetStatusThrowsExceptionForInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> topUp.setStatus("INVALID_STATUS"));
    }

    @Test
    void testConstructorThrowsExceptionForEmptyObserverUsers() {
        ArrayList<User> emptyObserverUsers = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () ->
                new TopUp(UUID.randomUUID(), emptyObserverUsers, 5000L, LocalDate.now(), "user456"));
    }
}
