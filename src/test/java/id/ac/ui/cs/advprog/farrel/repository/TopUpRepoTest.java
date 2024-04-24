package id.ac.ui.cs.advprog.farrel.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.model.User;
import id.ac.ui.cs.advprog.farrel.repository.TopUpRepository;

public class TopUpRepoTest {
    TopUpRepository topUpRepository;
    ArrayList<TopUp> topUpList;

    @BeforeEach
    void setUp() {
        TopUpRepository topUpRepository = new TopUpRepository();

        ArrayList<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("pembeli");
        user1.setUserId("1");
        User user2 = new User();
        user2.setName("staff");
        user2.setUserId("2");
        users.add(user1);
        users.add(user2);

        topUpList = new ArrayList<>();
        TopUp topUp1 = new TopUp("1", users, 200000,LocalDate.of(2004, 2, 18), "1" );
        TopUp topUp2 = new TopUp("2", users, 300000,LocalDate.of(2004, 2, 19), "2" );
        topUpList.add(topUp1);
        topUpList.add(topUp2);
    }

    @Test
    void testCreate() {
        TopUp topUp = topUpList.get(1);
        TopUp result = topUpRepository.create(topUp);

        assertEquals(topUp.getTopUpId(), result.getTopUpId());
        assertEquals(topUp.getTransactionTime(), result.getTransactionTime());
        assertEquals(topUp.getStatus(), result.getStatus());
    }

    @Test
    void testFindIfIdNotFound(){
        for (TopUp topUp : topUpList) {
            topUpRepository.create(topUp);
        }

        TopUp findResult = topUpRepository.findTopUp("zczc");
        assertNull(findResult);
    }

    @Test
    void testFindIfIdFound(){
        for (TopUp topUp : topUpList) {
            topUpRepository.create(topUp);
        }

        TopUp findResult = topUpRepository.findTopUp(topUpList.get(1).getTopUpId());
        assertEquals(findResult, topUpList.get(1));
    }
}

