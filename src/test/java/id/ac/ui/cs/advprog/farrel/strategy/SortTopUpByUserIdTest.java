package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTopUpByUserIdTest {

    private SortTopUpByUserId sortTopUpByUserId;

    @BeforeEach
    public void setUp() {
        sortTopUpByUserId = new SortTopUpByUserId();
    }

    @Test
    public void testSort() {
        List<TopUp> topUpList = new ArrayList<>();
        TopUp topUp1 = new TopUp();
        topUp1.setUserOwnerId("userB");
        TopUp topUp2 = new TopUp();
        topUp2.setUserOwnerId("userC");
        TopUp topUp3 = new TopUp();
        topUp3.setUserOwnerId("userA");

        topUpList.add(topUp1);
        topUpList.add(topUp2);
        topUpList.add(topUp3);

        sortTopUpByUserId.sort(topUpList);

        assertEquals("userA", topUpList.get(0).getUserOwnerId());
        assertEquals("userB", topUpList.get(1).getUserOwnerId());
        assertEquals("userC", topUpList.get(2).getUserOwnerId());
    }
}
