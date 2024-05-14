package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortByTransactionTimeTest {

    @Test
    public void testSortByTransactionTime() {
        // Arrange
        TopUp topUp1 = new TopUp();
        topUp1.setTransactionTime(LocalDate.of(2024, 5, 1));
        TopUp topUp2 = new TopUp();
        topUp2.setTransactionTime(LocalDate.of(2024, 5, 3));
        TopUp topUp3 = new TopUp();
        topUp3.setTransactionTime(LocalDate.of(2024, 5, 2));
        List<TopUp> topUpList = new ArrayList<>();
        topUpList.add(topUp1);
        topUpList.add(topUp2);
        topUpList.add(topUp3);

        SortTopUpByTransactionTime sortByTransactionTime = new SortTopUpByTransactionTime();

        sortByTransactionTime.sort(topUpList);

        assertEquals(topUp1, topUpList.get(0));
        assertEquals(topUp3, topUpList.get(1));
        assertEquals(topUp2, topUpList.get(2));
    }

    @Test
    public void testSortByTransactionTimeEmptyList() {
        List<TopUp> topUpList = new ArrayList<>();
        SortTopUpByTransactionTime sortByTransactionTime = new SortTopUpByTransactionTime();

        sortByTransactionTime.sort(topUpList);

        assertEquals(0, topUpList.size());
    }
}
