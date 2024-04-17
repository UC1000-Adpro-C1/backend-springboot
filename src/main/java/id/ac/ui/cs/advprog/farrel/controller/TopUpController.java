package id.ac.ui.cs.advprog.farrel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.ac.ui.cs.advprog.farrel.model.TopUp;
import id.ac.ui.cs.advprog.farrel.service.TopUpService;
import id.ac.ui.cs.advprog.farrel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/topup")
public class TopUpController {
    @Autowired
    private TopUpService topUpService;

    @Autowired
    private UserService UserService;

    @PostMapping("/update/{topUpId}")
    public String updateTopUpPost(@ModelAttribute TopUp updatedTopUp, Model model){
        topUpService.updateTopUp(updatedTopUp.getTopUpId(), updatedTopUp);
        UserService.topUpUpdate(updatedTopUp);
        
        return "";
    }
    
}
