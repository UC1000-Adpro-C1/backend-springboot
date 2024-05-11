package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StaffRestController {

    @Autowired
    private StaffRestService topUpService;

    @PostMapping("/topup")
    public ResponseEntity<TopUp> createTopUp(@RequestBody TopUp topUp) {
        TopUp createdTopUp = topUpService.createTopUp(topUp);
        return new ResponseEntity<>(createdTopUp, HttpStatus.CREATED);
    }

    @GetMapping("/topups")
    public ResponseEntity<List<TopUp>> getAllTopUps() {
        List<TopUp> allTopUps = topUpService.findAll();
        return new ResponseEntity<>(allTopUps, HttpStatus.OK);
    }

    @GetMapping("/topup/{id}")
    public ResponseEntity<TopUp> getTopUpById(@PathVariable("id") UUID id) {
        try {
            TopUp topUp = topUpService.findById(id);
            return new ResponseEntity<>(topUp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Listing " + id + " not found");
        }
    }

    @GetMapping("/topups/pending")
    public ResponseEntity<List<TopUp>> getPendingTopUps() {
        List<TopUp> pendingTopUps = topUpService.findByStatus(TopUpStatus.PENDING.name());
        return new ResponseEntity<>(pendingTopUps, HttpStatus.OK);
    }

    @GetMapping("/topups/non-pending")
    public ResponseEntity<List<TopUp>> getNonPendingTopUps() {
        List<TopUp> nonPendingTopUps = topUpService.findByStatusNot(TopUpStatus.PENDING.name());
        return new ResponseEntity<>(nonPendingTopUps, HttpStatus.OK);
    }

    @PutMapping("/topup/{id}/update-status")
    public ResponseEntity<TopUp> updateStatus(@PathVariable("id") UUID id, @RequestParam("status") String newStatus) {
        TopUp updatedTopUp = topUpService.updateStatus(id, newStatus);
        return new ResponseEntity<>(updatedTopUp, HttpStatus.OK);
    }
}