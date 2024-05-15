package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.StaffPaymentRepository;
import id.ac.ui.cs.advprog.farrel.repository.StaffTopUpRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffRestServiceTest {

    @Mock
    private StaffTopUpRepository topUpRepository;

    @Mock
    private StaffPaymentRepository paymentRepository;

    @InjectMocks
    private StaffRestService staffRestService;

    @Test
    public void testFindAllTopUps() {
        List<TopUp> topUps = new ArrayList<>();
        when(topUpRepository.findAll()).thenReturn(topUps);

        assertEquals(topUps, staffRestService.findAllTopUps());
    }

    @Test
    public void testFindTopUpById() {
        TopUp topUp = new TopUp();
        UUID id = UUID.randomUUID();
        when(topUpRepository.findById(id)).thenReturn(java.util.Optional.of(topUp));

        assertEquals(topUp, staffRestService.findTopUpById(id));
    }

    @Test
    public void testFindTopUpByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(topUpRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> staffRestService.findTopUpById(id));
    }

    @Test
    public void testFindTopUpByStatus() {
        List<TopUp> topUps = new ArrayList<>();
        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatus(status)).thenReturn(topUps);

        assertEquals(topUps, staffRestService.findTopUpByStatus(status));
    }

    @Test
    public void testFindTopUpByStatusNot() {
        List<TopUp> topUps = new ArrayList<>();
        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        assertEquals(topUps, staffRestService.findTopUpByStatusNot(status));
    }

    @Test
    public void testUpdateTopUpStatus() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setStatus(TopUpStatus.PENDING.name());
        when(topUpRepository.findById(id)).thenReturn(java.util.Optional.of(topUp));

        staffRestService.updateTopUpStatus(id, TopUpStatus.SUCCESS.name());

        assertEquals(TopUpStatus.SUCCESS.name(), topUp.getStatus());
        verify(topUpRepository, times(1)).save(topUp);
    }

    @Test
    public void testCreatePayment() {
        Payment payment = new Payment.PaymentBuilder(UUID.randomUUID(), 1000L, UUID.randomUUID().toString())
                .handledBy("staff123")
                .build();

        staffRestService.createPayment(payment);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();

        assertEquals(payment.getAmount(), capturedPayment.getAmount());
        assertEquals(payment.getUserId(), capturedPayment.getUserId());
        assertEquals(payment.getHandledBy(), capturedPayment.getHandledBy());
        assertEquals(PaymentStatus.PENDING.getValue(), capturedPayment.getStatus());
    }

    @Test
    public void testFindAllPayments() {
        List<Payment> payments = new ArrayList<>();
        when(paymentRepository.findAll()).thenReturn(payments);

        assertEquals(payments, staffRestService.findAllPayments());
    }

    @Test
    public void testFindPaymentById() {
        Payment payment = new Payment();
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(java.util.Optional.of(payment));

        assertEquals(payment, staffRestService.findPaymentById(id));
    }

    @Test
    public void testFindPaymentByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> staffRestService.findPaymentById(id));
    }

    @Test
    public void testFindPaymentByStatus() {
        List<Payment> payments = new ArrayList<>();
        String status = PaymentStatus.PENDING.name();
        when(paymentRepository.findByStatus(status)).thenReturn(payments);

        assertEquals(payments, staffRestService.findPaymentByStatus(status));
    }

    @Test
    public void testFindPaymentByStatusNot() {
        List<Payment> payments = new ArrayList<>();
        String status = PaymentStatus.PENDING.name();
        when(paymentRepository.findByStatusNot(status)).thenReturn(payments);

        assertEquals(payments, staffRestService.findPaymentByStatusNot(status));
    }

    @Test
    public void testUpdatePaymentStatus() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment.PaymentBuilder(id, 1000L, UUID.randomUUID().toString())
                .status(PaymentStatus.PENDING.name())
                .build();

        when(paymentRepository.findById(id)).thenReturn(java.util.Optional.of(payment));

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);

        staffRestService.updatePaymentStatus(id, PaymentStatus.SUCCESS.name());

        verify(paymentRepository).save(paymentCaptor.capture());
        Payment updatedPayment = paymentCaptor.getValue();

        assertEquals(PaymentStatus.SUCCESS.name(), updatedPayment.getStatus());
    }
}