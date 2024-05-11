//package id.ac.ui.cs.advprog.farrel.controller;
//
//import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
//import id.ac.ui.cs.advprog.farrel.model.Staff;
//import id.ac.ui.cs.advprog.farrel.model.TopUp;
//import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class StaffRestControllerTest {
//
//    @Mock
//    private StaffRestService staffRestService;
//
//    @InjectMocks
//    private StaffRestController staffRestController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testUpdateTopUpStatus() {
//        // Setup
//        UUID id = UUID.randomUUID();
//        TopUpStatus status = TopUpStatus.SUCCESS;
//        Staff staff = new Staff();
//        ResponseEntity<TopUp> expectedResponse = ResponseEntity.ok(new TopUp());
//
//        when(staffRestService.updateTopUpStatus(id, status, staff)).thenReturn(new TopUp());
//
//        // Execute
//        ResponseEntity<TopUp> response = staffRestController.updateTopUpStatus(id, status, staff);
//
//        // Verify
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        verify(staffRestService, times(1)).updateTopUpStatus(id, status, staff);
//    }
//
//    @Test
//    void testGetPendingTopUps() {
//        // Setup
//        List<TopUp> topUps = new ArrayList<>();
//        topUps.add(new TopUp());
//        ResponseEntity<List<TopUp>> expectedResponse = ResponseEntity.ok(topUps);
//
//        when(staffRestService.getPendingTopUps()).thenReturn(topUps);
//
//        // Execute
//        ResponseEntity<List<TopUp>> response = staffRestController.getPendingTopUps();
//
//        // Verify
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        verify(staffRestService, times(1)).getPendingTopUps();
//    }
//
//    @Test
//    void testGetNonPendingTopUps() {
//        // Setup
//        List<TopUp> topUps = new ArrayList<>();
//        topUps.add(new TopUp());
//        ResponseEntity<List<TopUp>> expectedResponse = ResponseEntity.ok(topUps);
//
//        when(staffRestService.getNonPendingTopUps()).thenReturn(topUps);
//
//        // Execute
//        ResponseEntity<List<TopUp>> response = staffRestController.getNonPendingTopUps();
//
//        // Verify
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        verify(staffRestService, times(1)).getNonPendingTopUps();
//    }
//}
