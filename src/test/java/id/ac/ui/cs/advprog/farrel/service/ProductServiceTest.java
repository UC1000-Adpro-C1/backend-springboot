package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.repository.SellRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private SellRepository sellRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product1 = new Product.ProductBuilder("Product 1", 10).build();
        Product product2 = new Product.ProductBuilder("Product 2", 20).build();
        List<Product> expectedProducts = Arrays.asList(product1, product2);

        doReturn(expectedProducts).when(sellRepository).findAll();
        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(expectedProducts, actualProducts);
    }
}