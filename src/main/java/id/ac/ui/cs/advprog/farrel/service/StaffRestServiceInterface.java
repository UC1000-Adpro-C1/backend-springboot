package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Payment;
import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.List;
import java.util.UUID;

public interface StaffRestServiceInterface {
    public List<TopUp> findAllTopUps();
    public TopUp findTopUpById(UUID id);
    public List<TopUp> findTopUpByStatus(String status, String sortingStrategy);
    public List<TopUp> findTopUpByStatusNot(String status, String sortingStrategy);
    public void sortTopUps(List<TopUp> topUps, String sortingStrategy);
    public TopUp updateTopUpStatus(UUID id, String newStatus);
    public Payment createPayment(Payment payment);
    public List<Payment> findAllPayments();
    public Payment findPaymentById(UUID id);
    public List<Payment> findPaymentByStatus(String status, String sortingStrategy);
    public List<Payment> findPaymentByStatusNot(String status, String sortingStrategy);
    public void sortPayments(List<Payment> payments, String sortingStrategy);
    public Payment updatePaymentStatus(UUID id, String newStatus);
}
