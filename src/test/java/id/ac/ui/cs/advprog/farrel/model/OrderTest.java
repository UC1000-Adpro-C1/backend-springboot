package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class OrderTest {
    Order order;
    Order.OrderBuilder orderBuilder;
    Product product;

    @BeforeEach
    void SetUp(){
        Product.ProductBuilder ProductBuilder = new Product.ProductBuilder("Red Sweater", 12000);
        this.product = ProductBuilder.build();
        this.orderBuilder = new Order.OrderBuilder(product.getProductId());
        this.order = orderBuilder.setBuyerId("FakeBuyerId")
                .build();
    }
    @Test
    void testGetOrderId(){
        assertNotNull(this.order.getId());
    }

    @Test
    void testGetOrderItemId(){
        assertEquals(product.getProductId(), this.order.getItemId());
    }

    @Test
    void testGetOrderStatusDefault(){
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), this.order.getStatus());
    }

    @Test
    void testGetOrderStatusSuccess(){
        order.setStatus("SUCCESS");
        assertEquals(OrderStatus.SUCCESS.getValue(), this.order.getStatus());
    }

    @Test
    void testGetOrderBuyer(){
        assertEquals("FakeBuyerId", this.order.getBuyerId());
    }
}

