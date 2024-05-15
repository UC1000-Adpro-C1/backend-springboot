package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.PaymentStatus;
import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.StaffPaymentRepository;
import id.ac.ui.cs.advprog.farrel.repository.StaffTopUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class StaffRestService implements StaffRestServiceInterface{

    @Autowired
    private StaffTopUpRepository topUpRepository;

    @Autowired
    private StaffPaymentRepository paymentRepository;

    @Override
    public List<TopUp> findAllTopUps() {
        return topUpRepository.findAll();
    }

    @Override
    public TopUp findTopUpById(UUID id) {
        return topUpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TopUp with ID " + id + " not found"));
    }

    @Override
    public List<TopUp> findTopUpByStatus(String status) {
        return topUpRepository.findByStatus(status);
    }

    @Override
    public List<TopUp> findTopUpByStatusNot(String status) {
        return topUpRepository.findByStatusNot(status);
    }

    @Override
    public TopUp updateTopUpStatus(UUID id, String newStatus) {
        TopUp topUp = findTopUpById(id);

        if (!topUp.getStatus().equals(TopUpStatus.PENDING.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update status of non-pending TopUp");
        }

        if (!TopUpStatus.contains(newStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }

        topUp.setStatus(newStatus);
        topUpRepository.save(topUp);
        return topUp;
    }

    @Override
    public Payment createPayment(Payment payment) {
        Payment newPayment = new Payment.PaymentBuilder(UUID.randomUUID(), payment.getAmount(), payment.getUserId())
                .handledBy(payment.getHandledBy())
                .build();

        paymentRepository.save(newPayment);
        return newPayment;
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findPaymentById(UUID id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment with ID " + id + " not found"));
    }

    @Override
    public List<Payment> findPaymentByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    public List<Payment> findPaymentByStatusNot(String status) {
        return paymentRepository.findByStatusNot(status);
    }

    @Override
    public Payment updatePaymentStatus(UUID id, String newStatus) {
        Payment payment = findPaymentById(id);

        if (!payment.getStatus().equals(PaymentStatus.PENDING.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update status of non-pending Payment");
        }

        if (!PaymentStatus.contains(newStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }

        payment = new Payment.PaymentBuilder(payment.getId(), payment.getAmount(), payment.getUserId())
            .handledBy(payment.getHandledBy())
            .status(newStatus)
            .build();

        paymentRepository.save(payment);
        return payment;
    }
}