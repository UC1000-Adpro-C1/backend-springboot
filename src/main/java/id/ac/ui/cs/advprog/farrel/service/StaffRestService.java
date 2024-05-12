package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
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

    @Override
    public TopUp createTopUp(TopUp topUp) {
        topUp.setTopUpId(UUID.randomUUID());
        topUp.setStatus(TopUpStatus.PENDING.name());
        topUpRepository.save(topUp);
        return topUp;
    }

    @Override
    public List<TopUp> findAll() {
        return topUpRepository.findAll();
    }

    @Override
    public TopUp findById(UUID id) {
        return topUpRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TopUp with ID " + id + " not found"));
    }

    @Override
    public List<TopUp> findByStatus(String status) {
        return topUpRepository.findByStatus(status);
    }

    @Override
    public List<TopUp> findByStatusNot(String status) {
        return topUpRepository.findByStatusNot(status);
    }

    @Override
    public TopUp updateStatus(UUID id, String newStatus) {
        TopUp topUp = findById(id);

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
}