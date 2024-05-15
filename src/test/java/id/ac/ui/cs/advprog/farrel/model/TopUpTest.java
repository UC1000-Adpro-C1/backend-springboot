package id.ac.ui.cs.advprog.farrel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TopUpTest {
    private ArrayList<User> users;

    @BeforeEach
    void setUp() {
        this.users = new ArrayList<>();
        User user1 = new User();
        user1.setName("pembeli");
        user1.setUserId("1");
        User user2 = new User();
        user2.setName("staff");
        user2.setUserId("2");
        this.users.add(user1);
        this.users.add(user2);
    }


    @Test
    void testCreateTopUpEmptyUser() {
        this.users.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            TopUp topUp = new TopUp("1", users, 200000,LocalDate.of(2004, 2, 18), "1" );
        });
    }

    @Test
    void testCreateTopUpDefaultStatus() {
        TopUp topUp = new TopUp("1", users, 200000, LocalDate.of(2004, 2, 18), "1" );
        assertSame(this.users, topUp.getObserverUsers());
        assertEquals(2, topUp.getObserverUsers().size());
        assertEquals("pembeli", topUp.getObserverUsers().get(0).getName());
        assertEquals("staff", topUp.getObserverUsers().get(1).getName());

        assertEquals("1", topUp.getTopUpId());
        assertEquals(LocalDate.of(2004, 2, 18), topUp.getTransactionTime());
        assertEquals("PENDING", topUp.getStatus());
    }
}

