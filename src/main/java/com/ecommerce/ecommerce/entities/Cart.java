package com.ecommerce.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer cartId;
  @Getter @Setter private Integer amount;
  @Getter @Setter private double price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id")
  @JsonIgnore
  @Getter @Setter private Invoice invoiceCart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  @JsonIgnore
  @Getter @Setter private Client clientCart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  @JsonIgnore
  @Getter @Setter private Product productCart;

}