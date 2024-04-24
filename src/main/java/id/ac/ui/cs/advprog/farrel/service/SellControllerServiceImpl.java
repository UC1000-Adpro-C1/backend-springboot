package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SellControllerServiceImpl implements SellControllerService {
    @Autowired
    private SellRepository productRepository;

    @Override
    public Product create(Product product){
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll(){
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product delete(Product product) {
        return productRepository.delete(product);
    }

    public Product edit(Product product){
        productRepository.edit(product);
        return product;
    }

    public Product findById(String productId){
        return productRepository.findById(productId);
    }
}
