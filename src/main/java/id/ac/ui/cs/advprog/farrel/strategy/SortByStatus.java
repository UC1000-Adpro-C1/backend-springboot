package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByStatus implements TopUpSortingStrategy {
    @Override
    public void sort(List<TopUp> topUpList) {
        Collections.sort(topUpList, Comparator.comparing(TopUp::getStatus));
    }
}
