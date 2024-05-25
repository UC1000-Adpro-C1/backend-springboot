package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
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
    private StaffRestService staffRestService;

    @GetMapping("/topups")
    public ResponseEntity<List<TopUp>> getAllTopUps() {
        List<TopUp> allTopUps = staffRestService.findAllTopUps();
        return new ResponseEntity<>(allTopUps, HttpStatus.OK);
    }

    @GetMapping("/topup/{id}")
    public ResponseEntity<TopUp> getTopUpById(@PathVariable("id") UUID id) {
        try {
            TopUp topUp = staffRestService.findTopUpById(id);
            return new ResponseEntity<>(topUp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id TopUp " + id + " not found");
        }
    }

    @GetMapping("/topups/pending")
    public ResponseEntity<List<TopUp>> getPendingTopUps(@RequestParam(defaultValue = "") String sort) {
        List<TopUp> pendingTopUps = staffRestService.findTopUpByStatus(TopUpStatus.PENDING.name(), sort);
        return new ResponseEntity<>(pendingTopUps, HttpStatus.OK);
    }

    @GetMapping("/topups/non-pending")
    public ResponseEntity<List<TopUp>> getNonPendingTopUps() {
        List<TopUp> nonPendingTopUps = staffRestService.findTopUpByStatusNot(TopUpStatus.PENDING.name());
        return new ResponseEntity<>(nonPendingTopUps, HttpStatus.OK);
    }

    @PutMapping("/topup/{id}/update-status/success")
    public ResponseEntity<TopUp> updateTopUpStatusToSuccess(@PathVariable("id") UUID id) {
        TopUp updatedTopUp = staffRestService.updateTopUpStatus(id, TopUpStatus.SUCCESS.name());
        return new ResponseEntity<>(updatedTopUp, HttpStatus.OK);
    }

    @PutMapping("/topup/{id}/update-status/failed")
    public ResponseEntity<TopUp> updateTopUpStatusToFailed(@PathVariable("id") UUID id) {
        TopUp updatedTopUp = staffRestService.updateTopUpStatus(id, TopUpStatus.FAILED.name());
        return new ResponseEntity<>(updatedTopUp, HttpStatus.OK);
    }

    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = staffRestService.createPayment(payment);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> allPayments = staffRestService.findAllPayments();
        return new ResponseEntity<>(allPayments, HttpStatus.OK);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") UUID id) {
        try {
            Payment payment = staffRestService.findPaymentById(id);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Payment " + id + " not found");
        }
    }

    @GetMapping("/payments/pending")
    public ResponseEntity<List<Payment>> getPendingPayment() {
        List<Payment> pendingPayments = staffRestService.findPaymentByStatus(PaymentStatus.PENDING.name());
        return new ResponseEntity<>(pendingPayments, HttpStatus.OK);
    }

    @GetMapping("/payments/non-pending")
    public ResponseEntity<List<Payment>> getNonPendingPayments() {
        List<Payment> nonPendingPayments = staffRestService.findPaymentByStatusNot(PaymentStatus.PENDING.name());
        return new ResponseEntity<>(nonPendingPayments, HttpStatus.OK);
    }

    @PutMapping("/payment/{id}/update-status/success")
    public ResponseEntity<Payment> updatePaymentStatusToSuccess(@PathVariable("id") UUID id) {
        Payment updatedPayment = staffRestService.updatePaymentStatus(id, PaymentStatus.SUCCESS.name());
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @PutMapping("/payment/{id}/update-status/failed")
    public ResponseEntity<Payment> updatePaymentStatusToFailed(@PathVariable("id") UUID id) {
        Payment updatedPayment = staffRestService.updatePaymentStatus(id, PaymentStatus.FAILED.name());
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

}