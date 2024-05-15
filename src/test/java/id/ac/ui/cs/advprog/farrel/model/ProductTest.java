package id.ac.ui.cs.advprog.farrel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.farrel.model.Product;
import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    Product product;
    Product.ProductBuilder builder;

    @BeforeEach
    void SetUp(){
        this.builder = new Product.ProductBuilder("Sampo cap Bambang", 12000);
        this.product = builder.setStockQuantity(99)
                .setDescription("Sampo cap bambangg")
                .setImageUrl("google.com")
                .setSellerId("FakeId")
                .build();
    }
    @Test
    void testGetProductId(){
        assertNotNull(this.product.getProductId());
    }

    @Test
    void testGetProductName(){
        assertEquals("Sampo cap Bambang", this.product.getProductName());
    }
    @Test
    void testGetProductDescription(){
        assertEquals("Sampo cap bambangg", this.product.getDescription());
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
    @Test
    void testGetProductSeller(){
        assertEquals("FakeId", this.product.getSellerId());
    }
}
