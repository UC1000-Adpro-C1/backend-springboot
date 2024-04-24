package id.ac.ui.cs.advprog.farrel.repository;

import id.ac.ui.cs.advprog.farrel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Iterator;


@Repository
public class ProductRepository {
    List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductId() == null) {
            UUID uuid = UUID.randomUUID();
            product.setProductId(uuid.toString());
        }
        productData.add(product);
        return product;
    }

    public Product edit(Product updatedProduct){
        if(updatedProduct.getStockQuantity() < 0){
            updatedProduct.setStockQuantity(0);
        }
        for (int i = 0; i < productData.size(); i++) {
            Product product = productData.get(i);
            if(product.getProductId().equals(updatedProduct.getProductId())){
                productData.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    public Product delete(Product item) {
        Product product = findById(item.getProductId());
        if (product != null) {
            productData.remove(product);
            return product;
        }
        return null;
    }

    public Product findById(String productId){
        for(Product product: productData){
            if(product.getProductId().equals(productId)){
                return product;
            }
        }
        return null;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}

