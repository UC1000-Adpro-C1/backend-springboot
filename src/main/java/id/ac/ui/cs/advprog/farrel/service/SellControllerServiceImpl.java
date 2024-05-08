package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SellControllerServiceImpl implements SellControllerService {

    @Autowired
    private SellRepository productRepository;

    @Override
    @Async
    public CompletableFuture<Product> create(Product product){
        productRepository.save(product);
        return CompletableFuture.completedFuture(product);
    }

    @Override
    @Async
    public CompletableFuture<List<Product>> findAll(){
        return CompletableFuture.completedFuture(productRepository.findAll());
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(String id){
        productRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Product> update(Product product){
        productRepository.save(product);
        return CompletableFuture.completedFuture(product);
    }

    @Override
    @Async
    public CompletableFuture<Optional<Product>> findById(String id){

        return CompletableFuture.completedFuture(productRepository.findById(id));
    }
}