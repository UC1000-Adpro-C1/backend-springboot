package id.ac.ui.cs.advprog.farrel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StaffRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StaffRestService staffRestService;

    @InjectMocks
    private StaffRestController staffRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(staffRestController).build();
    }

    @Test
    public void testCreateTopUp() throws Exception {
        TopUp topUp = new TopUp();
        topUp.setAmount(10000);

        when(staffRestService.createTopUp(any(TopUp.class))).thenReturn(topUp);

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

        when(staffRestService.findAllTopUps()).thenReturn(Arrays.asList(topUp1, topUp2));

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

        when(staffRestService.findTopUpById(id)).thenReturn(topUp);

        mockMvc.perform(get("/api/topup/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(10000));
    }

    @Test
    public void testUpdateTopUpStatus() throws Exception {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setAmount(10000);

        when(staffRestService.updateTopUpStatus(id, "SUCCESS")).thenReturn(topUp);

        mockMvc.perform(put("/api/topup/{id}/update-status/success", id)
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(10000));
    }

    @Test
    public void testCreatePayment() {
        Payment payment = new Payment();
        when(staffRestService.createPayment(any(Payment.class))).thenReturn(payment);

        ResponseEntity<Payment> response = staffRestController.createPayment(payment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(payment, response.getBody());
    }

    @Test
    public void testGetAllPayments() {
        List<Payment> payments = Arrays.asList(new Payment(), new Payment());
        when(staffRestService.findAllPayments()).thenReturn(payments);

        ResponseEntity<List<Payment>> response = staffRestController.getAllPayments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(payments, response.getBody());
    }

    @Test
    public void testGetPaymentById() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment();
        when(staffRestService.findPaymentById(id)).thenReturn(payment);

        ResponseEntity<Payment> response = staffRestController.getPaymentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(payment, response.getBody());
    }

    @Test
    public void testGetPendingPayment() {
        List<Payment> pendingPayments = Arrays.asList(new Payment(), new Payment());
        when(staffRestService.findPaymentByStatus(PaymentStatus.PENDING.name())).thenReturn(pendingPayments);

        ResponseEntity<List<Payment>> response = staffRestController.getPendingPayment();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pendingPayments, response.getBody());
    }

    @Test
    public void testGetNonPendingPayments() {
        List<Payment> nonPendingPayments = Arrays.asList(new Payment(), new Payment());
        when(staffRestService.findPaymentByStatusNot(PaymentStatus.PENDING.name())).thenReturn(nonPendingPayments);

        ResponseEntity<List<Payment>> response = staffRestController.getNonPendingPayments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nonPendingPayments, response.getBody());
    }

    @Test
    public void testUpdatePaymentStatusToSuccess() {
        UUID id = UUID.randomUUID();
        Payment updatedPayment = new Payment();
        when(staffRestService.updatePaymentStatus(id, PaymentStatus.SUCCESS.name())).thenReturn(updatedPayment);

        ResponseEntity<Payment> response = staffRestController.updatePaymentStatusToSuccess(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPayment, response.getBody());
    }

    @Test
    public void testUpdateStatusToFailed() {
        UUID id = UUID.randomUUID();
        Payment updatedPayment = new Payment();
        when(staffRestService.updatePaymentStatus(id, PaymentStatus.FAILED.name())).thenReturn(updatedPayment);

        ResponseEntity<Payment> response = staffRestController.updateStatusToFailed(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPayment, response.getBody());
    }
}