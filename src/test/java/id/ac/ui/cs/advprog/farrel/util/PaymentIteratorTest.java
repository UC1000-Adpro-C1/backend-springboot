//package id.ac.ui.cs.advprog.farrel.util;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//class PaymentIteratorTest {
//    private List<Payment> payments;
//    private PaymentIterator paymentIterator;
//
//    @BeforeEach
//    public void setUp() {
//        payments = new ArrayList<Payment>();
//        payments.add(new Payment(UUID.randomUUID(), "PENDING"));
//        payments.add(new Payment(UUID.randomUUID(), "SUCCESS"));
//        payments.add(new Payment(UUID.randomUUID(), "PENDING"));
//        paymentIterator = new PaymentIterator(payments);
//    }
//
//    @Test
//    public void testFindByStatusPending() {
//        ArrayList<Payment> pendingPayments = paymentIterator.findByStatus("PENDING");
//        assertEquals(2, pendingPayments.size());
//        assertEquals("PENDING", pendingPayments.get(0).getStatus());
//        assertEquals("PENDING", pendingPayments.get(1).getStatus());
//    }
//
//    @Test
//    public void testFindByStatusNotPending() {
//        ArrayList<Payment> nonPendingPayments = paymentIterator.findNotByStatus("PENDING");
//        assertEquals(1, nonPendingPayments.size());
//        assertEquals("SUCCESS", nonPendingPayments.get(0).getStatus());
//    }
//
//    @Test
//    public void testFindByIdFound() {
//        UUID id = payments.get(0).getId();
//        Payment foundPayment = (Payment) paymentIterator.findById(id);
//        assertNotNull(foundPayment);
//        assertEquals(id, foundPayment.getId());
//    }
//
//    @Test
//    public void testFindByIdNotFound() {
//        UUID id = UUID.randomUUID();
//        TopUp foundPayment = (Payment) paymentIterator.findById(id);
//        assertNull(foundPayment);
//    }
//}