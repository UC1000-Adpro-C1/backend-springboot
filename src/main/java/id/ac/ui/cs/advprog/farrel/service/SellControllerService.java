package id.ac.ui.cs.advprog.farrel.service;

import java.util.List;

import id.ac.ui.cs.advprog.farrel.model.Product;

public interface SellControllerService {
    public Product create(Product product);
    public List<Product> findAll();
    public Product delete(Product product);
    public Product edit(Product product);
}
