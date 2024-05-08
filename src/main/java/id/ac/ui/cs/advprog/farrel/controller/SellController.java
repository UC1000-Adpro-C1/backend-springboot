package id.ac.ui.cs.advprog.farrel.controller;
import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.service.SellControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/product", produces="application/json")
@CrossOrigin(origins="*")
public class SellController {

    @Autowired
    SellControllerService productService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> createProduct(@RequestBody Product product){
        Map<String, Object> res = new HashMap<>();
        return productService.create(product)
                .thenApply(createdProduct -> {
                    res.put("product", createdProduct);
                    res.put("message", "Product Created Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    res.put("error", e.getMessage());
                    res.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> deleteProduct(@PathVariable("id") String id){
        Map<String, Object> res = new HashMap<>();
        return productService.delete(id)
                .thenApply(result -> {
                    res.put("code", HttpStatus.OK.value());
                    res.put("message", "Listing Deleted Successfully");
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                })
                .exceptionally(e -> {
                    res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    res.put("error", e.getMessage());
                    res.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
                });
    }

    @PutMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateProduct(@RequestBody Product product){
        Map<String, Object> res = new HashMap<>();
        return productService.update(product)
                .thenApply(updatedProduct -> {
                    res.put("product", updatedProduct);
                    res.put("message", "Product ID " + updatedProduct.getProductId() +" updated Successfully");
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                })
                .exceptionally(e -> {
                    res.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    res.put("error", e.getMessage());
                    res.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
                });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Product>>> findAllProduct(){
        return productService.findAll()
                .thenApplyAsync(products -> ResponseEntity.ok(products))
                .exceptionally(exception -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put("error", exception.getCause() != null ? exception.getCause().getMessage() : "Unknown error");
                    response.put("message", "Something went wrong with the server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
                });
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> findById(@PathVariable("id") String id){
        Map<String, Object> response = new HashMap<>();
        return productService.findById(id)
                .thenApply(product -> {
                    if (product.isEmpty()){
                        response.put("code", HttpStatus.NOT_FOUND.value());
                        response.put("message", "Product with ID " + id + " not found.");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                    return ResponseEntity.ok(product.get());
                })
                .exceptionally(e -> {
                    response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.put("error", e.getMessage());
                    response.put("message", "Something Wrong With Server");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                });
    }
}