package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortNonPendingPaymentByStatus implements PaymentSortingStrategy {
    @Override
    public void sort(List<Payment> paymentList) {
        List<Payment> nonPendingPayments = paymentList.stream()
                .filter(payment -> !payment.getStatus().equals(PaymentStatus.PENDING.name()))
                .sorted(Comparator.comparing(payment -> {
                    if (payment.getStatus().equals(PaymentStatus.SUCCESS.name())) {
                        return 0;
                    } else {
                        return 1;
                    }
                }))
                .collect(Collectors.toList());

        paymentList.clear();
        paymentList.addAll(nonPendingPayments);
    }
}