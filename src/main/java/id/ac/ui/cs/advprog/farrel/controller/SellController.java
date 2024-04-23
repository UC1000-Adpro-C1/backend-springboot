package id.ac.ui.cs.advprog.farrel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sell")
public class SellController {

    @GetMapping("/listSales")
    public ResponseEntity<String> listSales() {
        String responseBody = "This is the page to view the list of sales for all users.";
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/saleDetail/{saleId}")
    public ResponseEntity<String> saleDetail(@PathVariable String saleId) {
        String responseBody = "This is the page to view the sale with id-" + saleId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/saleEdit/{saleId}")
    public ResponseEntity<String> saleEdit(@PathVariable String saleId) {
        String responseBody = "This is the page to edit the status of sale with id-" + saleId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/listProducts")
    public ResponseEntity<String> listProducts() {
        String responseBody = "This is the page to view the list of products being sold.";
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/productDetail/{productId}")
    public ResponseEntity<String> productDetail(@PathVariable String productId) {
        String responseBody = "This is the page to view the details of product with id-" + productId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/productEdit/{productId}")
    public ResponseEntity<String> productEdit(@PathVariable String productId) {
        String responseBody = "This is the page to edit the details of product with id-" + productId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
