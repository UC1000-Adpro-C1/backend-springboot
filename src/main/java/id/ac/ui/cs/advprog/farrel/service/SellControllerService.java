package id.ac.ui.cs.advprog.farrel.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import id.ac.ui.cs.advprog.farrel.model.Product;

public interface SellControllerService {
    CompletableFuture<Product> create(Product listing);
    CompletableFuture<List<Product>> findAll();
    CompletableFuture<Void> delete(String id);
    CompletableFuture<Optional<Product>> findById(String id);
    CompletableFuture<Product> update(Product listing);
    CompletableFuture<List<Product>> findBySellerId(String id);
}
