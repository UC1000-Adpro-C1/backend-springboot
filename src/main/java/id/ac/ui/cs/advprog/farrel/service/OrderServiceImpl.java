package id.ac.ui.cs.advprog.farrel.service;

import id.ac.ui.cs.advprog.farrel.model.Product;
import id.ac.ui.cs.advprog.farrel.model.Order;
import id.ac.ui.cs.advprog.farrel.repository.SellRepository;
import id.ac.ui.cs.advprog.farrel.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order update(Order order) {
        orderRepository.save(order);
        return order;
    }
}