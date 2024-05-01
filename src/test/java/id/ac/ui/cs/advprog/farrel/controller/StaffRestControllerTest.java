package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.StaffService;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StaffRestControllerTest {

    @Mock
    private TopUpIterator topUpIterator;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffRestController staffRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(staffRestController).build();
    }

    @Test
    void testTopUpHistory() throws Exception {
        List<TopUp> topUpList = new ArrayList<>();
        when(topUpIterator.findNotByStatus("PENDING")).thenReturn(topUpList);

        mockMvc.perform(get("/api/staff/topup-history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(topUpIterator, times(1)).findNotByStatus("PENDING");
    }

    @Test
    void testOnGoingTopUp() throws Exception {
        List<TopUp> onGoingTopUpList = new ArrayList<>();
        when(topUpIterator.findByStatus("PENDING")).thenReturn(onGoingTopUpList);

        mockMvc.perform(get("/api/staff/on-going-topup"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(topUpIterator, times(1)).findByStatus("PENDING");
    }

    @Test
    void testUpdateTopUpStatus() throws Exception {
        UUID topUpId = UUID.randomUUID();
        String status = "SUCCESS";

        mockMvc.perform(post("/api/staff/update-topup-status")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("topUpId", topUpId.toString())
                        .param("status", status))
                .andExpect(status().isOk());

        verify(staffService, times(1)).approveTopUp("staff1", topUpId);

        status = "FAILED";
        mockMvc.perform(post("/api/staff/update-topup-status")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("topUpId", topUpId.toString())
                        .param("status", status))
                .andExpect(status().isOk());

        verify(staffService, times(1)).rejectTopUp("staff1", topUpId);
    }
}
