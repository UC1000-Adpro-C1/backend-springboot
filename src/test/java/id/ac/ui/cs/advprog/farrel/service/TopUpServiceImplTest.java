package id.ac.ui.cs.advprog.farrel.service;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.TopUpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopUpServiceImplTest {
    @InjectMocks
    TopUpServiceImpl topUpService;

    @Mock
    TopUpRepository topUpRepository;

    TopUp topUp;

    @BeforeEach
    void SetUp(){
        topUp = new TopUp();
        topUp.setAmount(500);
        topUp.setUserOwnerId("halo");
    }

    @Test
    void testCreateAndFind() {

        when(topUpRepository.save(topUp)).thenReturn(topUp);
        topUpService.createTopUp(topUp);

        when(topUpRepository.findByUserId(topUp.getUserOwnerId())).thenReturn(List.of(topUp));
        List<TopUp> topUpList = topUpService.findTopUpByUserId(topUp.getUserOwnerId());

        assertFalse(topUpList.isEmpty());
        TopUp savedTopUp = topUpList.getFirst();
        assertEquals(topUp.getTopUpId(), savedTopUp.getTopUpId());
        assertEquals(topUp.getStatus(), savedTopUp.getStatus());
        assertEquals(topUp.getUserOwnerId(), savedTopUp.getUserOwnerId());
        assertEquals(topUp.getAmount(), savedTopUp.getAmount());
        assertEquals(topUp.getTransactionTime(), savedTopUp.getTransactionTime());
    }

    @Test
    void testFindTopUpById() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        when(topUpRepository.findById(id)).thenReturn(Optional.of(topUp));

        TopUp foundTopUp = topUpService.findTopUpById(id);

        assertEquals(id, foundTopUp.getTopUpId());
        verify(topUpRepository, times(1)).findById(id);
    }

    @Test
    void testFindTopUpByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(topUpRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            topUpService.findTopUpById(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("TopUp with ID " + id + " not found", exception.getReason());
        verify(topUpRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteTopUp() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        topUp.setStatus(TopUpStatus.PENDING.name());
        when(topUpRepository.findById(id)).thenReturn(Optional.of(topUp));

        topUpService.deleteTopUp(id);

        verify(topUpRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTopUpNonPending() {
        UUID id = UUID.randomUUID();
        TopUp topUp = new TopUp();
        topUp.setTopUpId(id);
        topUp.setStatus(TopUpStatus.SUCCESS.name());
        when(topUpRepository.findById(id)).thenReturn(Optional.of(topUp));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            topUpService.deleteTopUp(id);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Cannot delete non-pending TopUp", exception.getReason());
        verify(topUpRepository, never()).deleteById(id);
    }
}