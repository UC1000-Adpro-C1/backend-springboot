package id.ac.ui.cs.advprog.farrel.strategy;

import id.ac.ui.cs.advprog.farrel.model.*;
import java.util.List;

public interface ProductSortingStrategy{
    List<Product> sort(List<Product> products);
}