package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class StaffPaymentRepositoryTest {

    @Autowired
    private StaffPaymentRepository paymentRepository;

    @Test
    @DirtiesContext
    public void testFindByStatus() {
        Payment payment1 = new Payment.PaymentBuilder(UUID.randomUUID(), 10000L, "user123")
                .handledBy("staff1")
                .status(PaymentStatus.PENDING.getValue())
                .build();
        Payment payment2 = new Payment.PaymentBuilder(UUID.randomUUID(), 15000L, "user456")
                .handledBy("staff2")
                .status(PaymentStatus.SUCCESS.getValue())
                .build();
        Payment payment3 = new Payment.PaymentBuilder(UUID.randomUUID(), 20000L, "user789")
                .handledBy("staff3")
                .status(PaymentStatus.FAILED.getValue())
                .build();

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);

        List<Payment> pendingPayments = paymentRepository.findByStatus(PaymentStatus.PENDING.getValue());
        List<Payment> successPayments = paymentRepository.findByStatus(PaymentStatus.SUCCESS.getValue());
        List<Payment> failedPayments = paymentRepository.findByStatus(PaymentStatus.FAILED.getValue());

        assertEquals(1, pendingPayments.size());
        assertEquals(payment1, pendingPayments.get(0));

        assertEquals(1, successPayments.size());
        assertEquals(payment2, successPayments.get(0));

        assertEquals(1, failedPayments.size());
        assertEquals(payment3, failedPayments.get(0));
    }

    @Test
    @DirtiesContext
    public void testFindByStatusNot() {
        Payment payment1 = new Payment.PaymentBuilder(UUID.randomUUID(), 10000L, "user123")
                .handledBy("staff1")
                .status(PaymentStatus.PENDING.getValue())
                .build();
        Payment payment2 = new Payment.PaymentBuilder(UUID.randomUUID(), 15000L, "user456")
                .handledBy("staff2")
                .status(PaymentStatus.SUCCESS.getValue())
                .build();
        Payment payment3 = new Payment.PaymentBuilder(UUID.randomUUID(), 20000L, "user789")
                .handledBy("staff3")
                .status(PaymentStatus.FAILED.getValue())
                .build();

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);

        List<Payment> nonPendingPayments = paymentRepository.findByStatusNot(PaymentStatus.PENDING.getValue());
        List<Payment> nonSuccessPayments = paymentRepository.findByStatusNot(PaymentStatus.SUCCESS.getValue());
        List<Payment> nonFailedPayments = paymentRepository.findByStatusNot(PaymentStatus.FAILED.getValue());

        assertEquals(2, nonPendingPayments.size());
        assertEquals(payment2, nonPendingPayments.get(0));
        assertEquals(payment3, nonPendingPayments.get(1));

        assertEquals(2, nonSuccessPayments.size());
        assertEquals(payment1, nonSuccessPayments.get(0));
        assertEquals(payment3, nonSuccessPayments.get(1));

        assertEquals(2, nonFailedPayments.size());
        assertEquals(payment1, nonFailedPayments.get(0));
        assertEquals(payment2, nonFailedPayments.get(1));
    }
}
