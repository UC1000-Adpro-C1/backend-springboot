package id.ac.ui.cs.advprog.farrel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SellControllerTest {

    private final SellController sellController = new SellController();

    @Test
    void testListSales() {
        ResponseEntity<String> response = sellController.listSales();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This is the page to view the list of sales for all users.", response.getBody());
    }

    @Test
    void testSaleDetail() {
        String saleId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = sellController.saleDetail(saleId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This is the page to view the sale with id-" + saleId, response.getBody());
    }

    @Test
    void testSaleEdit() {
        String saleId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = sellController.saleEdit(saleId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This is the page to edit the status of sale with id-" + saleId, response.getBody());
    }

    @Test
    void testListProducts() {
        ResponseEntity<String> response = sellController.listProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This is the page to view the list of products being sold.", response.getBody());
    }

    @Test
    void testProductDetail() {
        String productId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = sellController.productDetail(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This is the page to view the details of product with id-" + productId, response.getBody());
    }

    @Test
    void testProductEdit() {
        String productId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = sellController.productEdit(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This is the page to edit the details of product with id-" + productId, response.getBody());
    }
}
