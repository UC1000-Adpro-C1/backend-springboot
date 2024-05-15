package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.List;
import java.util.UUID;

public interface TopUpService {
    public TopUp createTopUp(TopUp topUp);
    public List<TopUp> findAllTopUps();
    public TopUp findTopUpById(UUID id);
    public List<TopUp> findTopUpByStatus(String status);
    public List<TopUp> findTopUpByStatusNot(String status);
    public TopUp updateTopUpStatus(UUID id, String newStatus);
}