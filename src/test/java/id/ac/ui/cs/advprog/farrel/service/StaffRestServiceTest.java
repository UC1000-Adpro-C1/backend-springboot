package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.StaffTopUpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StaffRestServiceTest {

    @Mock
    private StaffTopUpRepository topUpRepository;

    @InjectMocks
    private StaffRestService staffRestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTopUp() {
        TopUp topUp = new TopUp();
        topUp.setTopUpId(UUID.randomUUID());
        topUp.setAmount(10000);
        topUp.setStatus(TopUpStatus.PENDING.name());
        when(topUpRepository.save(any())).thenReturn(topUp);

        TopUp createdTopUp = staffRestService.createTopUp(topUp);

        assertEquals(topUp, createdTopUp);
        verify(topUpRepository, times(1)).save(any());
    }

    @Test
    public void testFindAll() {
        List<TopUp> topUpList = new ArrayList<>();
        when(topUpRepository.findAll()).thenReturn(topUpList);

        List<TopUp> foundTopUps = staffRestService.findAll();

        assertEquals(topUpList, foundTopUps);
        verify(topUpRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        topUp.setAmount(10000);
        topUp.setStatus(TopUpStatus.PENDING.name());
        when(topUpRepository.findById(String.valueOf(id))).thenReturn(Optional.of(topUp));

        TopUp foundTopUp = staffRestService.findById(id);

        assertEquals(topUp, foundTopUp);
        verify(topUpRepository, times(1)).findById(String.valueOf(id));
    }

    @Test
    public void testUpdateStatusSuccess() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        topUp.setAmount(10000);
        topUp.setStatus(TopUpStatus.PENDING.name());
        when(topUpRepository.findById(String.valueOf(id))).thenReturn(Optional.of(topUp));
        when(topUpRepository.save(any())).thenReturn(topUp);

        TopUp updatedTopUp = staffRestService.updateStatus(id, TopUpStatus.SUCCESS.name());

        assertEquals(TopUpStatus.SUCCESS.name(), updatedTopUp.getStatus());
        verify(topUpRepository, times(1)).findById(String.valueOf(id));
        verify(topUpRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateStatusNonPending() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        topUp.setAmount(10000);
        topUp.setStatus(TopUpStatus.SUCCESS.name());
        when(topUpRepository.findById(String.valueOf(id))).thenReturn(Optional.of(topUp));

        assertThrows(ResponseStatusException.class, () -> {
            staffRestService.updateStatus(id, TopUpStatus.FAILED.name());
        });
        verify(topUpRepository, times(1)).findById(String.valueOf(id));
        verify(topUpRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateStatusInvalidStatus() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        topUp.setAmount(10000);
        topUp.setStatus(TopUpStatus.PENDING.name());
        when(topUpRepository.findById(String.valueOf(id))).thenReturn(Optional.of(topUp));

        assertThrows(ResponseStatusException.class, () -> {
            staffRestService.updateStatus(id, "invalidStatus");
        });
        verify(topUpRepository, times(1)).findById(String.valueOf(id));
        verify(topUpRepository, times(0)).save(any());
    }
}