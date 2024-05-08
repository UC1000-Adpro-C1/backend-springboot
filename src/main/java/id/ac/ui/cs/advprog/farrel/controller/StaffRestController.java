package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/topup")
public class StaffRestController {

    private final StaffRestService staffRestService;

    @Autowired
    public StaffRestController(StaffRestService staffRestService) {
        this.staffRestService = staffRestService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopUp> updateTopUpStatus(@PathVariable("id") UUID id,
                                                   @RequestParam("status") TopUpStatus status,
                                                   @RequestBody Staff staff) {
        try {
            TopUp topUp = staffRestService.updateTopUpStatus(id, status, staff);
            return ResponseEntity.ok(topUp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TopUp>> getPendingTopUps() {
        List<TopUp> pendingTopUps = staffRestService.getPendingTopUps();
        return ResponseEntity.ok(pendingTopUps);
    }

    @GetMapping("/non-pending")
    public ResponseEntity<List<TopUp>> getNonPendingTopUps() {
        List<TopUp> nonPendingTopUps = staffRestService.getNonPendingTopUps();
        return ResponseEntity.ok(nonPendingTopUps);
    }
}