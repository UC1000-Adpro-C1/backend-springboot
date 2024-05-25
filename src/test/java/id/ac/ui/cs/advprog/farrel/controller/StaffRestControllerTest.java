package id.ac.ui.cs.advprog.farrel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void testGetTopUpById_WhenNoSuchElementExceptionThrown() {
        UUID id = UUID.randomUUID();
        when(staffRestService.findTopUpById(id)).thenThrow(new NoSuchElementException());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> staffRestController.getTopUpById(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id TopUp " + id + " not found", exception.getReason());
        verify(staffRestService, times(1)).findTopUpById(id);
    }

    @Test
    public void testGetPendingTopUpsNoSorting() {
        List<TopUp> pendingTopUps = new ArrayList<TopUp>();

        TopUp topUp1 = new TopUp();
        topUp1.setTopUpId(UUID.randomUUID());
        topUp1.setAmount(10000L);
        topUp1.setStatus(TopUpStatus.PENDING.name());

        TopUp topUp2 = new TopUp();
        topUp2.setTopUpId(UUID.randomUUID());
        topUp2.setAmount(10000L);
        topUp2.setStatus(TopUpStatus.PENDING.name());

        pendingTopUps.add(topUp1);
        pendingTopUps.add(topUp2);

        when(staffRestService.findTopUpByStatus(TopUpStatus.PENDING.name(), "")).thenReturn(pendingTopUps);

        ResponseEntity<List<TopUp>> responseEntity = staffRestController.getPendingTopUps("");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pendingTopUps, responseEntity.getBody());
        verify(staffRestService, times(1)).findTopUpByStatus(TopUpStatus.PENDING.name(), "");
    }

    @Test
    public void testGetNonPendingTopUps() {
        List<TopUp> nonPendingTopUps = new ArrayList<TopUp>();

        TopUp topUp1 = new TopUp();
        topUp1.setTopUpId(UUID.randomUUID());
        topUp1.setAmount(25000L);
        topUp1.setStatus(TopUpStatus.SUCCESS.name());

        TopUp topUp2 = new TopUp();
        topUp2.setTopUpId(UUID.randomUUID());
        topUp2.setAmount(20000L);
        topUp2.setStatus(TopUpStatus.FAILED.name());

        nonPendingTopUps.add(topUp1);
        nonPendingTopUps.add(topUp2);

        when(staffRestService.findTopUpByStatusNot(TopUpStatus.PENDING.name(), "")).thenReturn(nonPendingTopUps);

        ResponseEntity<List<TopUp>> responseEntity = staffRestController.getNonPendingTopUps("");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nonPendingTopUps, responseEntity.getBody());
        verify(staffRestService, times(1)).findTopUpByStatusNot(TopUpStatus.PENDING.name(), "");
    }

    @Test
    public void testUpdateTopUpStatusToSuccess() throws Exception {
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
    public void testUpdateTopUpStatusToFailed() {
        UUID id = UUID.randomUUID();
        TopUp updatedTopUp = new TopUp();
        updatedTopUp.setTopUpId(id);
        updatedTopUp.setAmount(10000L);
        updatedTopUp.setStatus(TopUpStatus.FAILED.name());
        when(staffRestService.updateTopUpStatus(id, TopUpStatus.FAILED.name())).thenReturn(updatedTopUp);

        ResponseEntity<TopUp> responseEntity = staffRestController.updateTopUpStatusToFailed(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTopUp, responseEntity.getBody());
        verify(staffRestService, times(1)).updateTopUpStatus(id, TopUpStatus.FAILED.name());
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
    public void testGetPaymentById_WhenNoSuchElementExceptionThrown() {
        UUID id = UUID.randomUUID();
        when(staffRestService.findPaymentById(id)).thenThrow(new NoSuchElementException());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> staffRestController.getPaymentById(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id Payment " + id + " not found", exception.getReason());
        verify(staffRestService, times(1)).findPaymentById(id);
    }

    @Test
    public void testGetPendingPayment() {
        List<Payment> pendingPayments = Arrays.asList(new Payment(), new Payment());
        when(staffRestService.findPaymentByStatus(PaymentStatus.PENDING.name(), "")).thenReturn(pendingPayments);

        ResponseEntity<List<Payment>> response = staffRestController.getPendingPayment("");

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

        ResponseEntity<Payment> response = staffRestController.updatePaymentStatusToFailed(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPayment, response.getBody());
    }
}