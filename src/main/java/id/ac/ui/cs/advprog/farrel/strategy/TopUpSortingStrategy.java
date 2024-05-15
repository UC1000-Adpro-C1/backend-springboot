package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.List;

public interface TopUpSortingStrategy {
    void sort(List<TopUp> topUpList);
}