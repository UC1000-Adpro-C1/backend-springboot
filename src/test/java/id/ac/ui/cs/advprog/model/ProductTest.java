package id.ac.ui.cs.advprog.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.model.Product.ProductBuilder;

import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    Product product;
    Product.ProductBuilder builder;

    @BeforeEach
    void SetUp(){
        this.builder = new Product.ProductBuilder("Red Sweater", 12000);
        this.product = builder.setStockQuantity(99)
                .setDescription("The color of the sweater is red")
                .build();
    }
    @Test
    void testGetProductId(){
        assertNotNull(this.product.getProductId());
    }

    @Test
    void testGetProductName(){
        assertEquals("Red Sweater", this.product.getProductName());
    }
    @Test
    void testGetProductDescription(){
        assertEquals("The color of the sweater is red", this.product.getDescription());
    }
    
    @Test
    void testGetProductPrice(){
        assertEquals(12000, this.product.getPrice());
    }
    @Test
    void testGetProductStock(){
        assertEquals(99, this.product.getStockQuantity());
    }
    @Test
    void testGetProductStockIfNegative(){
        this.product.setStockQuantity(-1);
        assertEquals(0, this.product.getStockQuantity());
    }
}
