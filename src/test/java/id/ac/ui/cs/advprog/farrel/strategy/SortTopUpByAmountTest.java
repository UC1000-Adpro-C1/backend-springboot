package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTopUpByAmountTest {

    @Test
    public void testSortByAmount() {
        List<TopUp> topUpList = new ArrayList<>();

        TopUp topUp1 = new TopUp();
        topUp1.setAmount(200);
        topUpList.add(topUp1);

        TopUp topUp2 = new TopUp();
        topUp2.setAmount(100);
        topUpList.add(topUp2);

        TopUp topUp3 = new TopUp();
        topUp3.setAmount(300);
        topUpList.add(topUp3);

        SortTopUpByAmount sortByAmount = new SortTopUpByAmount();
        sortByAmount.sort(topUpList);

        assertEquals(topUp2, topUpList.get(0));
        assertEquals(topUp1, topUpList.get(1));
        assertEquals(topUp3, topUpList.get(2));
    }

    @Test
    public void testSortByAmountWithSameValues() {
        List<TopUp> topUpList = new ArrayList<>();

        TopUp topUp1 = new TopUp();
        topUp1.setAmount(100);
        topUpList.add(topUp1);

        TopUp topUp2 = new TopUp();
        topUp2.setAmount(100);
        topUpList.add(topUp2);

        SortTopUpByAmount sortByAmount = new SortTopUpByAmount();
        sortByAmount.sort(topUpList);

        assertEquals(topUp1, topUpList.get(0));
        assertEquals(topUp2, topUpList.get(1));
    }
}