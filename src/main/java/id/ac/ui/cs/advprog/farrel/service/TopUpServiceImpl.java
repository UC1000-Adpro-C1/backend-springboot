package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.TopUpRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TopUpServiceImpl implements TopUpService {
    @Autowired
    private TopUpRepository topUpRepository;

    @Override
    public TopUp create(TopUp topUp) {
        topUpRepository.create(topUp);
        return topUp;
    }

    @Override
    public void updateTopUp(String topUpId, TopUp updatedTopUp) {
        topUpRepository.updateTopUp(topUpId, updatedTopUp);
    }

    
}
