package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.Payment;

import java.util.List;

public interface PaymentSortingStrategy {
    void sort(List<Payment> paymentList);
}