package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.TopUpRepository;
import id.ac.ui.cs.advprog.farrel.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TopUpServiceImpl implements TopUpService {
    @Autowired
    private TopUpRepository topUpRepository;
    private UserRepository userRepository;

    @Override
    public TopUp create(TopUp topUp) {
        //tambahkan code kalo user belum masuk di repo user (==null)
        topUpRepository.create(topUp);
        return topUp;
    }

    @Override
    public void updateTopUp(String topUpId, TopUp updatedTopUp) {
        topUpRepository.updateTopUp(updatedTopUp);
        userRepository.updateTopUpStatus(updatedTopUp);
    }

    @Override
    public TopUp findTopUp(String topUpId) {
        return topUpRepository.findTopUp(topUpId);
    }
}
