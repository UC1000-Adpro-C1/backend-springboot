package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortNonPendingPaymentByStatusTest {

    private SortNonPendingPaymentByStatus sortNonPendingPaymentByStatus;

    @BeforeEach
    public void setUp() {
        sortNonPendingPaymentByStatus = new SortNonPendingPaymentByStatus();
    }

    @Test
    public void testSort() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment.PaymentBuilder(UUID.randomUUID(), 1000L, "userC").status(PaymentStatus.FAILED.name()).handledBy("staff1").build());
        paymentList.add(new Payment.PaymentBuilder(UUID.randomUUID(), 2000L, "userA").status(PaymentStatus.SUCCESS.name()).handledBy("staff2").build());
        paymentList.add(new Payment.PaymentBuilder(UUID.randomUUID(), 1500L, "userB").status(PaymentStatus.FAILED.name()).handledBy("staff3").build());

        sortNonPendingPaymentByStatus.sort(paymentList);

        assertEquals("userA", paymentList.get(0).getUserId());
        assertEquals("userC", paymentList.get(1).getUserId());
        assertEquals("userB", paymentList.get(2).getUserId());
    }

}
