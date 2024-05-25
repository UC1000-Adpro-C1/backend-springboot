package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortPaymentByAmountTest {

    private SortPaymentByAmount sortPaymentByAmount;

    @BeforeEach
    public void setUp() {
        sortPaymentByAmount = new SortPaymentByAmount();
    }

    @Test
    public void testSort() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment.PaymentBuilder(UUID.randomUUID(), 1000L, "userC").handledBy("staff1").build());
        paymentList.add(new Payment.PaymentBuilder(UUID.randomUUID(), 2000L, "userA").handledBy("staff2").build());
        paymentList.add(new Payment.PaymentBuilder(UUID.randomUUID(), 1500L, "userB").handledBy("staff3").build());

        sortPaymentByAmount.sort(paymentList);

        assertEquals(1000L, paymentList.get(0).getAmount());
        assertEquals(1500L, paymentList.get(1).getAmount());
        assertEquals(2000L, paymentList.get(2).getAmount());
    }
}
