package id.ac.ui.cs.advprog.farrel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter @Getter
public class Product {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private Date startDate;
    private Date endDate;
    private int stockQuantity;

}
