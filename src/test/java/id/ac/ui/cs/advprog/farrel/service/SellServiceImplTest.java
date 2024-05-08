package id.ac.ui.cs.advprog.farrel.service;
import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.repository.SellRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SellServiceImplTest {
    @InjectMocks
    SellControllerServiceImpl productService;

    @Mock
    SellRepository productRepository;

    @BeforeEach
    void SetUp(){

    }

    @Test
    void testCreateAndFind() throws ExecutionException, InterruptedException{
        Product.ProductBuilder builder = new Product.ProductBuilder("Sampo cap Bambang", 12000);
        Product product = builder.setStockQuantity(99)
                .setDescription("Sampo cap Bambangg")
                .setImageUrl("google.com")
                .build();

        Mockito.when(productRepository.save(product)).thenReturn(product);
        productService.create(product);

        Mockito.when(productRepository.findAll()).thenReturn(List.of(product));
        CompletableFuture<List<Product>> productListFuture = productService.findAll();
        List<Product> productList = productListFuture.get();

        assertFalse(productList.isEmpty());
        Product savedProduct = productList.getFirst();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getStockQuantity(), savedProduct.getStockQuantity());
    }

    @Test
    void testFindAllIfEmpty() throws ExecutionException, InterruptedException {
        List<Product> productList = new ArrayList<>();
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        CompletableFuture<List<Product>> productsFuture = productService.findAll();
        List<Product> products = productsFuture.get();

        assertTrue(products.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() throws ExecutionException, InterruptedException {
        Product.ProductBuilder builder = new Product.ProductBuilder("Sampo cap Bambang", 12000);
        Product product1 = builder.setStockQuantity(99)
                .setDescription("Sampo cap Bambangg")
                .setImageUrl("google.com")
                .build();

        Mockito.when(productRepository.save(product1)).thenReturn(product1);
        productService.create(product1);

        Product product2 = builder.setStockQuantity(99)
                .setDescription("Sampo cap Paijo")
                .setImageUrl("Paijo.com")
                .build();
        Mockito.when(productRepository.save(product2)).thenReturn(product2);
        productService.create(product2);

        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        CompletableFuture<List<Product>> productListFuture = productService.findAll();
        List<Product> productList = productListFuture.get();

        assertFalse(productList.isEmpty());
        assertEquals(product1.getProductId(), productList.getFirst().getProductId());
        assertEquals(product2.getProductId(), productList.get(1).getProductId());
        assertFalse(productList.isEmpty());
    }

    @Test
    void testEditProduct() throws ExecutionException, InterruptedException {
        Product.ProductBuilder builder = new Product.ProductBuilder("Sampo cap Bambang", 12000);
        Product product = builder.setStockQuantity(99)
                .setDescription("Sampo cap Bambangg")
                .setImageUrl("google.com")
                .build();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Mockito.when(productRepository.save(product)).thenReturn(product);
        productService.create(product);

        builder = new Product.ProductBuilder("Sampo cap Paijo", 12000);
        Product editedProduct = builder.setStockQuantity(99)
                .setDescription("Sampo cap Paijoo")
                .setImageUrl("Paijo.com")
                .build();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        productService.update(editedProduct);

        Mockito.when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(editedProduct));
        CompletableFuture<Optional<Product>> resultProductFuture = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        Optional<Product> resultProduct = resultProductFuture.get();

        assertEquals(editedProduct, resultProduct.get());
        Mockito.verify(productRepository).save(editedProduct);
    }

    @Test
    void testDeleteProduct() {
        Product.ProductBuilder builder = new Product.ProductBuilder("Sampo cap Bambang", 12000);
        Product product1 = builder.setStockQuantity(99)
                .setDescription("Sampo cap Bambangg")
                .setImageUrl("google.com")
                .build();

        Mockito.when(productRepository.save(product1)).thenReturn(product1);
        productService.create(product1);

        productService.delete(product1.getProductId());

        Mockito.verify(productRepository).deleteById(product1.getProductId());
    }
}