package id.ac.ui.cs.advprog.farrel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private String id;
    private String userId;
    private List<CartItem> items = new ArrayList<>();

    public Cart(String id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }
}