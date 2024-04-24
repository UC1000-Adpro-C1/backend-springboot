package id.ac.ui.cs.advprog.farrel.model;

import java.util.ArrayList;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="review")
@Entity
public class Review{
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String reviewId;

    @NotNull
    @Size(max=100)
    @Column(name="userid", nullable=false)
    private String userId;

    @NotNull
    @Size(max=100)
    @Column(name="product", nullable=false)
    private String productId;

    @NotNull
    @Size(max=1)
    @Column(name="rating", nullable=false)
    private int rating;

    @NotNull
    @Size(max=300)
    @Column(name="review", nullable=false)
    private String review;
}