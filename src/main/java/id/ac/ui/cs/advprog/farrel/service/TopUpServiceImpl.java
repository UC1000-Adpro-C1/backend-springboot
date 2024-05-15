package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.repository.TopUpRepository;
import id.ac.ui.cs.advprog.farrel.service.TopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class TopUpServiceImpl implements TopUpService{

    @Autowired
    private TopUpRepository topUpRepository;

    @Override
    public TopUp createTopUp(TopUp topUp) {
        topUp.setTopUpId(UUID.randomUUID());
        topUp.setStatus(TopUpStatus.PENDING.name());
        topUpRepository.save(topUp);
        return topUp;
    }

    @Override
    public TopUp findTopUpById(UUID id) {
        return topUpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TopUp with ID " + id + " not found"));
    }

    @Override
    public List<TopUp> findTopUpByUserId(String userOwnerId) {
        return topUpRepository.findByUserId(userOwnerId);
    }

    @Override
    public void deleteTopUp(UUID id) {
        TopUp topUp = findTopUpById(id);

        if (!topUp.getStatus().equals(TopUpStatus.PENDING.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete non-pending TopUp");
        }

        topUpRepository.deleteById(id);
    }

}