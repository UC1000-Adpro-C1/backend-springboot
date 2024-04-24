//package id.ac.ui.cs.advprog.farrel.util;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//public class PaymentIterator {
//    private List<Payment> payments;
//    private int position = 0;
//
//    public PaymentIterator(List<Payment> payments) {
//        this.payments = payments;
//    }
//
//    public boolean hasNext() {
//        return position < payments.size();
//    }
//
//    public Payment next() {
//        if (hasNext()) {
//            return payments.get(position++);
//        }
//        return null;
//    }
//
//    public List<Payment> findByStatus(String status) {
//        List<Payment> result = new ArrayList<Payment>();
//        for (Payment payment : payments) {
//            if (payment.getStatus().equals(status)) {
//                result.add(payment);
//            }
//        }
//        return result;
//    }
//
//    public List<Payment> findNotByStatus(String status) {
//        List<Payment> result = new ArrayList<TopUp>();
//        for (Payment payment : payments) {
//            if (!payment.getStatus().equals(status)) {
//                result.add(payment);
//            }
//        }
//        return result;
//    }
//
//    public Payment findById(UUID id) {
//        for (Payment payment : payments) {
//            if (payment.getId().equals(id)) {
//                return payment;
//            }
//        }
//        return null;
//    }
//}
