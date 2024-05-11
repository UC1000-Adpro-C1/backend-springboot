package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortNonPendingTopUpByStatus implements TopUpSortingStrategy {
    @Override
    public void sort(List<TopUp> topUps) {
        List<TopUp> nonPendingTopUps = topUps.stream()
                .filter(topUp -> !topUp.getStatus().equals(TopUpStatus.PENDING.name()))
                .sorted(Comparator.comparing(topUp -> {
                    if (topUp.getStatus().equals(TopUpStatus.SUCCESS.name())) {
                        return 0;
                    } else {
                        return 1;
                    }
                }))
                .collect(Collectors.toList());

        topUps.clear();
        topUps.addAll(nonPendingTopUps);
    }
}