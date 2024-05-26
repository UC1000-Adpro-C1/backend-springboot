package id.ac.ui.cs.advprog.farrel.model;

import id.ac.ui.cs.advprog.farrel.model.state.ActiveCartState;
import id.ac.ui.cs.advprog.farrel.model.state.CartState;
import id.ac.ui.cs.advprog.farrel.model.state.CheckedOutCartState;
import id.ac.ui.cs.advprog.farrel.model.state.EmptyCartState;
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

    private Integer userId;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "state")
    private String stateString; // Store the state as a string

    @Transient
    private CartState state;

    public Cart() {
        this.state = new EmptyCartState();
        this.stateString = this.state.getClass().getSimpleName();
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
        this.stateString = state.getClass().getSimpleName();
    }

    public CartState getState() {
        if (this.state == null) {
            convertStringStateToCartState();
        }
        return this.state;
    }

    @PostLoad
    private void convertStringStateToCartState() {
        switch (this.stateString) {
            case "ActiveCartState":
                this.state = new ActiveCartState();
                break;
            case "CheckedOutCartState":
                this.state = new CheckedOutCartState();
                break;
            default:
                this.state = new EmptyCartState();
        }
    }
}
