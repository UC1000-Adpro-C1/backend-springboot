package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.TopUpService;
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
public class TopUpController {

    @Autowired
    private TopUpService topUpService;

    @PostMapping("/topup")
    public ResponseEntity<TopUp> createTopUp(@RequestBody TopUp topUp) {
        TopUp createdTopUp = topUpService.createTopUp(topUp);
        return new ResponseEntity<>(createdTopUp, HttpStatus.CREATED);
    }

    @GetMapping("/topups")
    public ResponseEntity<List<TopUp>> getAllTopUps() {
        List<TopUp> allTopUps = topUpService.findAllTopUps();
        return new ResponseEntity<>(allTopUps, HttpStatus.OK);
    }

    @GetMapping("/topup/{id}")
    public ResponseEntity<TopUp> getTopUpById(@PathVariable("id") UUID id) {
        try {
            TopUp topUp = topUpService.findTopUpById(id);
            return new ResponseEntity<>(topUp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id TopUp " + id + " not found");
        }
    }
    @GetMapping("/topups/{userOwnerId}")
    public ResponseEntity<List<TopUp>> getTopUpByUserId(@PathVariable("userOwnerId") String userOwnerId) {
        List<TopUp> userTopUps = topUpService.findTopUpByUserId(userOwnerId);
        return new ResponseEntity<>(userTopUps, HttpStatus.OK);
    }

            @GetMapping("/topups/pending")
    public ResponseEntity<List<TopUp>> getPendingTopUps() {
        List<TopUp> pendingTopUps = topUpService.findTopUpByStatus(TopUpStatus.PENDING.name());
        return new ResponseEntity<>(pendingTopUps, HttpStatus.OK);
    }

    @GetMapping("/topups/non-pending")
    public ResponseEntity<List<TopUp>> getNonPendingTopUps() {
        List<TopUp> nonPendingTopUps = topUpService.findTopUpByStatusNot(TopUpStatus.PENDING.name());
        return new ResponseEntity<>(nonPendingTopUps, HttpStatus.OK);
    }

    @PutMapping("/topup/{id}/update-status/success")
    public ResponseEntity<TopUp> updateTopUpStatusToSuccess(@PathVariable("id") UUID id) {
        TopUp updatedTopUp = topUpService.updateTopUpStatus(id, TopUpStatus.SUCCESS.name());
        return new ResponseEntity<>(updatedTopUp, HttpStatus.OK);
    }

    @PutMapping("/topup/{id}/update-status/failed")
    public ResponseEntity<TopUp> updateTopUpStatusToFailed(@PathVariable("id") UUID id) {
        TopUp updatedTopUp = topUpService.updateTopUpStatus(id, TopUpStatus.FAILED.name());
        return new ResponseEntity<>(updatedTopUp, HttpStatus.OK);
    }

}