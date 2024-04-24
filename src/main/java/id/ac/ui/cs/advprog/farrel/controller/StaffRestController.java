package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import id.ac.ui.cs.advprog.farrel.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {

    private TopUpIterator topUpIterator;
    private StaffService staffService;

    @GetMapping("/topup-history")
    public List<TopUp> topUpHistory() {
        return topUpIterator.findNotByStatus(TopUpStatus.PENDING.name());
    }

    @GetMapping("/on-going-topup")
    public List<TopUp> onGoingTopUp() {
        return topUpIterator.findByStatus(TopUpStatus.PENDING.name());
    }

    @PostMapping("/update-topup-status")
    public void updateTopUpStatus(@RequestParam("topUpId") UUID topUpId,
                                  @RequestParam("status") String status) {
        if (status.equals("SUCCESS")) {
            staffService.approveTopUp("staff1", topUpId);
        } else if (status.equals("FAILED")) {
            staffService.rejectTopUp("staff1", topUpId);
        }
    }
}