package id.ac.ui.cs.advprog.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    Product product;
    Product.ProductBuilder builder;

    @BeforeEach
    void SetUp(){
        this.builder = new Product.ListingBuilder("Red Sweater", 12000);
        this.product = builder.setId("eb558e9f-1c39-460e-8860-71af6af63bd6")
                .setStock(99)
                .setDescription("The color of the sweater is red")
                .setImageUrl("google.com");
    }
    @Test
    void testGetListingId(){
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getproductId());
    }

    @Test
    void testGetListingName(){
        assertEquals("Red Sweater", this.product.getproductName());
    }

    @Test
    void testGetListingDescription(){
        assertEquals("The color of the sweater is red", this.product.getDescription());
    }

    @Test
    void testGetListingPrice(){
        assertEquals(12000, this.product.getPrice());
    }

    @Test
    void testGetListingStock(){
        assertEquals(99, this.product.getStockQuantity());
    }

    @Test
    void testGetListingStockIfNegative(){
        this.product.setStock(-1);
        assertEquals(0, this.product.getStockQuantity());
    }

}
