package id.ac.ui.cs.advprog.farrel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartResponse {
    private UUID cartId;
    private Integer userId;
    private UUID itemId;
}
