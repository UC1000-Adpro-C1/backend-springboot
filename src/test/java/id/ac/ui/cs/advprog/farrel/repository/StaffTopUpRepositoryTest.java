package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StaffTopUpRepositoryTest {

    @Mock
    private StaffTopUpRepository repository;

    @InjectMocks
    private StaffRestService service;

    @Test
    public void testFindByStatus() {
        String status = "SUCCESS";
        List<TopUp> topUps = new ArrayList<>();

        TopUp topUp = new TopUp();
        topUp.setTopUpId(UUID.randomUUID());
        topUp.setAmount(150000);
        topUp.setStatus(TopUpStatus.SUCCESS.name());
        topUps.add(topUp);

        when(repository.findByStatus(status)).thenReturn(topUps);

        List<TopUp> foundTopUps = service.findTopUpByStatus(status, "");

        assertThat(foundTopUps.size()).isEqualTo(1);
        assertThat(foundTopUps.get(0).getStatus()).isEqualTo(status);
    }

    @Test
    public void testFindByStatusNot() {
        // Arrange
        String status = "PENDING";
        List<TopUp> topUps = new ArrayList<>();

        TopUp topUp = new TopUp();
        topUp.setTopUpId(UUID.randomUUID());
        topUp.setAmount(150000);
        topUp.setStatus(TopUpStatus.FAILED.name());
        topUps.add(topUp);
        when(repository.findByStatusNot(status)).thenReturn(topUps);

        List<TopUp> foundTopUps = service.findTopUpByStatusNot(status, "");

        assertThat(foundTopUps.size()).isEqualTo(1);
        assertThat(foundTopUps.get(0).getStatus()).isNotEqualTo(status);
    }
}
