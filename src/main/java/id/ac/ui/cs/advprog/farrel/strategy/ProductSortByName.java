package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.Product;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSortByName implements ProductSortingStrategy {
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(product -> product.getProductName().toLowerCase()))
                .collect(Collectors.toList());
    }
}