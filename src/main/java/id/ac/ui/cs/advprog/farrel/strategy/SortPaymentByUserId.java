package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.Payment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortPaymentByUserId implements PaymentSortingStrategy {
    @Override
    public void sort(List<Payment> topUpList) {
        Collections.sort(topUpList, Comparator.comparing(Payment::getUserId));
    }
}