package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.model.state.CartState;
import id.ac.ui.cs.advprog.farrel.model.state.EmptyCartState;
import id.ac.ui.cs.advprog.farrel.model.state.CheckedOutCartState;
import id.ac.ui.cs.advprog.farrel.model.state.ActiveCartState;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cartId;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    @Transient
    private CartState state;

    public Cart() {
        this.state = new EmptyCartState(); // Initial state
    }

    public void addItem(CartItem item) {
        state.addItem(this, item);
    }

    public void removeItem(UUID itemId) {
        state.removeItem(this, itemId);
    }

    public void checkout() {
        state.checkout(this);
    }

    public void setState(CartState state) {
        this.state = state;
    }
}
