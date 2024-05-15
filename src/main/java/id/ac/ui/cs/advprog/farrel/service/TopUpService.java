package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

import java.util.List;
import java.util.UUID;

public interface TopUpService {
    public TopUp createTopUp(TopUp topUp);
    public TopUp findTopUpById(UUID id);
    public List<TopUp> findTopUpByUserId(String userOwnerId);
    public void deleteTopUp(UUID id);
}
