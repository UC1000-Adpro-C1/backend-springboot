package id.ac.ui.cs.advprog.farrel.service;


import id.ac.ui.cs.advprog.farrel.model.TopUp;


public interface TopUpService {
    public TopUp create(TopUp topUp);
    public void updateTopUp(String topUpId, TopUp updatedTopUp);
    public TopUp findTopUp(String topUpId);
}