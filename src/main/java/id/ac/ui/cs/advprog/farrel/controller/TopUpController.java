package id.ac.ui.cs.advprog.farrel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.TopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/topup")
public class TopUpController {
    @Autowired
    private TopUpService topUpService;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        TopUp topUp = new TopUp(null, null, 0, null, null);
        model.addAttribute("topUp", topUp);
        return "createTopUp";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute TopUp topUp, Model model) {
        topUpService.create(topUp);
        return "";
    }

    @GetMapping("/update/{topUpId}")
    public String editProductListPage(Model model, @PathVariable("topUpId") String topUpId) {
        TopUp topUp = topUpService.findTopUp(topUpId);
        model.addAttribute("topUp", topUp);
        return "topup_update";
    }

    @PostMapping("/update/")
    public String updateTopUpPost(@ModelAttribute TopUp updatedTopUp, Model model){
        topUpService.updateTopUp(updatedTopUp.getTopUpId(), updatedTopUp);
        return "";
    }
    
}
