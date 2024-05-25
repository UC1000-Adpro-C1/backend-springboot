package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.StaffPaymentRepository;
import id.ac.ui.cs.advprog.farrel.repository.StaffTopUpRepository;
import id.ac.ui.cs.advprog.farrel.strategy.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

    private SortTopUpByTransactionTime sortByTransactionTime = new SortTopUpByTransactionTime();
    private SortTopUpByUserId sortByUserId = new SortTopUpByUserId();
    private SortTopUpByAmount sortTopUpByAmount = new SortTopUpByAmount();
    private SortNonPendingTopUpByStatus sortNonPendingTopUpByStatus = new SortNonPendingTopUpByStatus();
    private SortPaymentByUserId sortPaymentByUserId = new SortPaymentByUserId();
    private SortPaymentByAmount sortPaymentByAmount = new SortPaymentByAmount();

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
    public void testFindTopUpByStatusNoSorting() {
        List<TopUp> topUps = new ArrayList<>();
        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatus(status)).thenReturn(topUps);

        assertEquals(topUps, staffRestService.findTopUpByStatus(status, ""));
    }

    @Test
    public void testFindTopUpByStatusWithTransactionTimeDesc() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setTransactionTime(LocalDate.of(2023, 1, 1));
        TopUp topUp2 = new TopUp();
        topUp2.setTransactionTime(LocalDate.of(2023, 1, 2));
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatus(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatus(status, "transactionTimeDesc");

        assertEquals(topUp1, result.get(0));
        assertEquals(topUp2, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusWithOwnerId() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setUserOwnerId(UUID.randomUUID().toString());
        TopUp topUp2 = new TopUp();
        topUp2.setUserOwnerId(UUID.randomUUID().toString());
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatus(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatus(status, "ownerId");

        sortByUserId.sort(topUps);
        assertEquals(topUps, result);
    }

    @Test
    public void testFindTopUpByStatusWithTransactionTimeAsc() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setTransactionTime(LocalDate.of(2023, 1, 2));
        TopUp topUp2 = new TopUp();
        topUp2.setTransactionTime(LocalDate.of(2023, 1, 1));
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatus(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatus(status, "transactionTimeAsc");

        List<TopUp> expected = new ArrayList<>(topUps);
        sortByTransactionTime.sort(expected);
        expected = expected.reversed();
        assertEquals(expected, result);
        assertEquals(topUp1, result.get(0));
        assertEquals(topUp2, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusSortByAmount() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setAmount(2000);
        TopUp topUp2 = new TopUp();
        topUp2.setAmount(1000);
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatus(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatus(status, "amount");

        List<TopUp> expected = new ArrayList<>(topUps);
        sortTopUpByAmount.sort(expected);
        assertEquals(expected, result);
        assertEquals(topUp2, result.get(0));
        assertEquals(topUp1, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusNotNoSorting() {
        List<TopUp> topUps = new ArrayList<>();
        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        assertEquals(topUps, staffRestService.findTopUpByStatusNot(status, ""));
    }

    @Test
    public void testFindTopUpByStatusNotSortByTransactionTimeAsc() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setTransactionTime(LocalDate.of(2023, 1, 2));
        topUp1.setStatus(TopUpStatus.SUCCESS.name());
        TopUp topUp2 = new TopUp();
        topUp2.setTransactionTime(LocalDate.of(2023, 1, 1));
        topUp2.setStatus(TopUpStatus.FAILED.name());
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatusNot(status, "transactionTimeAsc");

        List<TopUp> expected = new ArrayList<>(topUps);
        sortByTransactionTime.sort(expected);
        expected = expected.reversed();
        assertEquals(expected, result);
        assertEquals(topUp1, result.get(0));
        assertEquals(topUp2, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusNotSortByTransactionTimeDesc() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setStatus(TopUpStatus.SUCCESS.name());
        topUp1.setTransactionTime(LocalDate.of(2023, 1, 1));
        TopUp topUp2 = new TopUp();
        topUp2.setStatus(TopUpStatus.FAILED.name());
        topUp2.setTransactionTime(LocalDate.of(2023, 1, 2));
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatusNot(status, "transactionTimeDesc");

        assertEquals(topUp1, result.get(0));
        assertEquals(topUp2, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusNotWithOwnerId() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setStatus(TopUpStatus.SUCCESS.name());
        topUp1.setUserOwnerId(UUID.randomUUID().toString());
        TopUp topUp2 = new TopUp();
        topUp2.setStatus(TopUpStatus.FAILED.name());
        topUp2.setUserOwnerId(UUID.randomUUID().toString());
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatusNot(status, "ownerId");

        sortByUserId.sort(topUps);
        assertEquals(topUps, result);
    }

    @Test
    public void testFindTopUpByStatusNotSortByAmount() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setStatus(TopUpStatus.SUCCESS.name());
        topUp1.setAmount(2000);
        TopUp topUp2 = new TopUp();
        topUp2.setStatus(TopUpStatus.FAILED.name());
        topUp2.setAmount(1000);
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatusNot(status, "amount");

        List<TopUp> expected = new ArrayList<>(topUps);
        sortTopUpByAmount.sort(expected);
        assertEquals(expected, result);
        assertEquals(topUp2, result.get(0));
        assertEquals(topUp1, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusNotSortStatus() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setStatus(TopUpStatus.SUCCESS.name());
        topUp1.setAmount(2000);
        TopUp topUp2 = new TopUp();
        topUp2.setStatus(TopUpStatus.FAILED.name());
        topUp2.setAmount(1000);
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatusNot(status, "status");

        List<TopUp> expected = new ArrayList<>(topUps);
        sortNonPendingTopUpByStatus.sort(expected);
        assertEquals(expected, result);
        assertEquals(topUp1, result.get(0));
        assertEquals(topUp2, result.get(1));
    }

    @Test
    public void testFindTopUpByStatusNotSortStatusReverse() {
        List<TopUp> topUps = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setStatus(TopUpStatus.SUCCESS.name());
        topUp1.setAmount(2000);
        TopUp topUp2 = new TopUp();
        topUp2.setStatus(TopUpStatus.FAILED.name());
        topUp2.setAmount(1000);
        topUps.add(topUp1);
        topUps.add(topUp2);

        String status = TopUpStatus.PENDING.name();
        when(topUpRepository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> result = staffRestService.findTopUpByStatusNot(status, "statusReverse");

        List<TopUp> expected = new ArrayList<>(topUps);
        sortNonPendingTopUpByStatus.sort(expected);
        Collections.reverse(expected);
        assertEquals(expected, result);
        assertEquals(topUp2, result.get(0));
        assertEquals(topUp1, result.get(1));
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
    public void testFindPaymentByStatusNoSorting() {
        List<Payment> payments = new ArrayList<>();
        String status = PaymentStatus.PENDING.name();
        when(paymentRepository.findByStatus(status)).thenReturn(payments);

        assertEquals(payments, staffRestService.findPaymentByStatus(status, ""));
    }

    @Test
    public void testFindPaymentByStatusWithUserIdSorting() {
        List<Payment> payments = new ArrayList<>();
        Payment payment1 = new Payment.PaymentBuilder(UUID.randomUUID(), 1000L, "user2")
                .handledBy("staff1")
                .status(PaymentStatus.PENDING.name())
                .build();
        Payment payment2 = new Payment.PaymentBuilder(UUID.randomUUID(), 2000L, "user1")
                .handledBy("staff2")
                .status(PaymentStatus.PENDING.name())
                .build();
        payments.add(payment1);
        payments.add(payment2);

        String status = PaymentStatus.PENDING.name();
        when(paymentRepository.findByStatus(status)).thenReturn(payments);

        List<Payment> result = staffRestService.findPaymentByStatus(status, "ownerId");

        sortPaymentByUserId.sort(payments);
        assertEquals(payments, result);
    }

    @Test
    public void testFindPaymentByStatusWithAmountSorting() {
        List<Payment> payments = new ArrayList<>();
        Payment payment1 = new Payment.PaymentBuilder(UUID.randomUUID(), 2000L, "user1")
                .handledBy("staff1")
                .status(PaymentStatus.PENDING.name())
                .build();
        Payment payment2 = new Payment.PaymentBuilder(UUID.randomUUID(), 1000L, "user2")
                .handledBy("staff2")
                .status(PaymentStatus.PENDING.name())
                .build();
        payments.add(payment1);
        payments.add(payment2);

        String status = PaymentStatus.PENDING.name();
        when(paymentRepository.findByStatus(status)).thenReturn(payments);

        List<Payment> result = staffRestService.findPaymentByStatus(status, "amount");

        sortPaymentByAmount.sort(payments);
        assertEquals(payments, result);
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