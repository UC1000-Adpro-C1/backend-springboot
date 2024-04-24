package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.service.StaffService;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StaffControllerTest {

    @Mock
    private TopUpIterator topUpIterator;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testTopUpHistory() {
        List<TopUp> topUpList = new ArrayList<>();
        when(topUpIterator.findNotByStatus(TopUpStatus.PENDING.name())).thenReturn(topUpList);

        assertEquals("topup-history", staffController.topUpHistory(model));
        verify(topUpIterator, times(1)).findNotByStatus(TopUpStatus.PENDING.name());
        verify(model, times(1)).addAttribute("topUpHistory", topUpList);
    }

    @Test
    void testOnGoingTopUp() {
        List<TopUp> onGoingTopUpList = new ArrayList<>();
        when(topUpIterator.findByStatus(TopUpStatus.PENDING.name())).thenReturn(onGoingTopUpList);

        assertEquals("on-going-topup", staffController.onGoingTopUp(model));
        verify(topUpIterator, times(1)).findByStatus(TopUpStatus.PENDING.name());
        verify(model, times(1)).addAttribute("onGoingTopUp", onGoingTopUpList);
    }

    @Test
    void testUpdateTopUpStatus() {
        UUID topUpId = UUID.randomUUID();
        String status = TopUpStatus.SUCCESS.name();
        String redirectUrl = "redirect:/staff/on-going-topup";

        assertEquals(redirectUrl, staffController.updateTopUpStatus(topUpId, status));
        verify(staffService, times(1)).approveTopUp("staff1", topUpId);

        status = TopUpStatus.FAILED.name();
        assertEquals(redirectUrl, staffController.updateTopUpStatus(topUpId, status));
        verify(staffService, times(1)).rejectTopUp("staff1", topUpId);
    }
}
