package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.List;
import java.util.UUID;

public interface StaffRestServiceInterface {
    public TopUp createTopUp(TopUp topUp);
    public List<TopUp> findAll();
    public TopUp findById(UUID id);
    public TopUp updateStatus(UUID id, String newStatus);
}