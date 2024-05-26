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
@CrossOrigin(origins = "*")
public class TopUpController {

    @Autowired
    private TopUpService topUpService;

    @PostMapping("/topup")
    public ResponseEntity<TopUp> createTopUp(@RequestBody TopUp topUp) {
        System.out.println("masuk");
        TopUp createdTopUp = topUpService.createTopUp(topUp);
        return new ResponseEntity<>(createdTopUp, HttpStatus.CREATED);
    }

    @GetMapping("/topups/{userOwnerId}")
    public ResponseEntity<List<TopUp>> getTopUpByUserId(@PathVariable("userOwnerId") String userOwnerId) {
        List<TopUp> userTopUps = topUpService.findTopUpByUserId(userOwnerId);
        return new ResponseEntity<>(userTopUps, HttpStatus.OK);
    }

    @PostMapping("/topups/cancel/{topUpId}")
    public ResponseEntity<Void> cancelTopUp(@PathVariable("topUpId") UUID topUpId) {
        try {
            TopUp topUp = topUpService.findTopUpById(topUpId);
            if (topUp.getStatus().equals(TopUpStatus.PENDING.name())) {
                topUpService.deleteTopUp(topUpId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PENDING top-ups can be cancelled.");
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Top-up not found.", e);
        }
    }
}