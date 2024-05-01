package id.ac.ui.cs.advprog.farrel.controller;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.util.TopUpIterator;
import id.ac.ui.cs.advprog.farrel.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private TopUpIterator topUpIterator;
    private StaffService staffService;

    @GetMapping("/topup-history")
    public String topUpHistory(Model model) {
        List<TopUp> topUpHistory = topUpIterator.findNotByStatus(TopUpStatus.PENDING.name());
        model.addAttribute("topUpHistory", topUpHistory);
        return "topup-history";
    }

    @GetMapping("/on-going-topup")
    public String onGoingTopUp(Model model) {
        List<TopUp> onGoingTopUp = topUpIterator.findByStatus(TopUpStatus.PENDING.name());
        model.addAttribute("onGoingTopUp", onGoingTopUp);
        return "on-going-topup";
    }

    @PostMapping("/update-topup-status")
    public String updateTopUpStatus(@RequestParam("topUpId") UUID topUpId,
                                    @RequestParam("status") String status) {
        if (status.equals("SUCCESS")) {
            staffService.approveTopUp("staff1", topUpId);
        } else if (status.equals("FAILED")) {
            staffService.rejectTopUp("staff1", topUpId);
        }

        return "redirect:/staff/on-going-topup";
    }
}