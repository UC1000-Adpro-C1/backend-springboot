package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.repository.SellRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final SellRepository sellRepository;

    public ProductServiceImpl(SellRepository sellRepository) {
        this.sellRepository = sellRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return sellRepository.findAll();
    }
}