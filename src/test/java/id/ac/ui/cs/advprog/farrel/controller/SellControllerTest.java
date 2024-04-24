package id.ac.ui.cs.advprog.farrel.controller;
import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.service.SellControllerService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class SellControllerTest {

    @InjectMocks
    SellController ProductController;

    @Mock
    private SellControllerService productService;

    @Mock
    private Model model;

    @Test
    void testCreateProductPage() {
        String result = ProductController.createProductPage(model);
        assertEquals("createProduct", result);
    }

    @Test
    void createProductPost() {
        Product product = new Product();
        String result = ProductController.createProductPost(product ,model);
        assertEquals("redirect:list", result);
    }

    @Test
    void testProductListPage() {
        String result = ProductController.productListPage(model);
        assertEquals("productList", result);
    }

    @Test
    void testEditProductPage() {
        String result = ProductController.editProductPage(model, "1");
        assertEquals("editProduct", result);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        String result = ProductController.editProductPost(product, model, "1");
        assertEquals("redirect:../list", result);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        String result = ProductController.deleteProductGet(product, model, "1");
        assertEquals("redirect:/product/list", result);
    }
}