package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortNonPendingTopUpByStatusTest {

    @Test
    public void testSortNonPendingTopUpByStatus() {
        TopUp pendingTopUp = new TopUp();
        pendingTopUp.setStatus(TopUpStatus.PENDING.name());
        TopUp successTopUp = new TopUp();
        successTopUp.setStatus(TopUpStatus.SUCCESS.name());
        TopUp failedTopUp = new TopUp();
        failedTopUp.setStatus(TopUpStatus.FAILED.name());
        List<TopUp> topUpList = new ArrayList<>();
        topUpList.add(pendingTopUp);
        topUpList.add(successTopUp);
        topUpList.add(failedTopUp);

        SortNonPendingTopUpByStatus sortNonPendingTopUpByStatus = new SortNonPendingTopUpByStatus();

        sortNonPendingTopUpByStatus.sort(topUpList);

        assertEquals(successTopUp, topUpList.get(0));
        assertEquals(failedTopUp, topUpList.get(1));
    }

    @Test
    public void testSortNonPendingTopUpByStatusEmptyList() {
        List<TopUp> topUpList = new ArrayList<>();
        SortNonPendingTopUpByStatus sortNonPendingTopUpByStatus = new SortNonPendingTopUpByStatus();

        sortNonPendingTopUpByStatus.sort(topUpList);

        assertEquals(0, topUpList.size());
    }
}
