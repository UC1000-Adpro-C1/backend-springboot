package id.ac.ui.cs.advprog.farrel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StaffRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StaffRestService topUpService;

    @InjectMocks
    private StaffRestController topUpController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(topUpController).build();
    }

    @Test
    public void testCreateTopUp() throws Exception {
        TopUp topUp = new TopUp();
        topUp.setAmount(10000);

        when(topUpService.createTopUp(any(TopUp.class))).thenReturn(topUp);

        mockMvc.perform(post("/api/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(topUp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(10000));
    }

    @Test
    public void testGetAllTopUps() throws Exception {
        TopUp topUp1 = new TopUp();
        topUp1.setAmount(10000);

        TopUp topUp2 = new TopUp();
        topUp2.setAmount(20000);

        when(topUpService.findAll()).thenReturn(Arrays.asList(topUp1, topUp2));

        mockMvc.perform(get("/api/topups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(10000))
                .andExpect(jsonPath("$[1].amount").value(20000));
    }

    @Test
    public void testGetTopUpById() throws Exception {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setAmount(10000);

        when(topUpService.findById(id)).thenReturn(topUp);

        mockMvc.perform(get("/api/topup/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(10000));
    }

    @Test
    public void testUpdateStatus() throws Exception {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setAmount(10000);

        when(topUpService.updateStatus(id, "SUCCESS")).thenReturn(topUp);

        mockMvc.perform(put("/api/topup/{id}/update-status", id)
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(10000));
    }
}
