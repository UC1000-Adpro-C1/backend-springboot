package id.ac.ui.cs.advprog.farrel.controller;
import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.service.SellControllerService;
import id.ac.ui.cs.advprog.farrel.service.SellControllerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class SellController {
    @Autowired
    private SellControllerService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/delete/{idProduct}")
    public String deleteProductGet(@ModelAttribute Product product, Model model, @PathVariable("idProduct") String productId) {
        product.setProductId(productId);
        service.delete(product);
        return "redirect:/product/list";
    }

    @GetMapping("/edit/{idProduct}")
    public String editProductPage(Model model, @PathVariable("idProduct") String productId) {
        Product product = new Product();
        product.setProductId(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{idProduct}")
    public String editProductPost(@ModelAttribute Product product, Model model, @PathVariable("idProduct") String productId) {
        product.setProductId(productId);
        service.edit(product);
        return "redirect:../list";
    }
}

