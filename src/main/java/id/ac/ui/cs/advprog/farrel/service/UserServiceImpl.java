package id.ac.ui.cs.advprog.farrel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.farrel.repository.UserRepository;
import id.ac.ui.cs.advprog.farrel.model.TopUp;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void topUpUpdate(TopUp updatedTopUp) {
        userRepository.updateTopUpStatus(updatedTopUp);
    }

    
}
