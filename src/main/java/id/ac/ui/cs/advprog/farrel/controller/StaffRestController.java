package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.model.Staff;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.StaffRestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {

    private final StaffRestService staffRestService;

    public StaffRestController(StaffRestService staffRestService) {
        this.staffRestService = staffRestService;
    }

    @GetMapping("/topup-history")
    public List<TopUp> topUpHistory() {
        return staffRestService.getTopUpHistory();
    }

    @GetMapping("/on-going-topup")
    public List<TopUp> onGoingTopUp() {
        return staffRestService.getOnGoingTopUp();
    }

    @PostMapping("/update-topup-status")
    public void updateTopUpStatus(@RequestParam("topUpId") UUID topUpId,
                                  @RequestParam("status") String status,
                                  @RequestParam("staffUsername") String staffUsername) {
        staffRestService.updateTopUpStatus(staffUsername, topUpId, status);
    }

}
