package com.ecommerce.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts") @NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {
  @Schema(description = "identificador único del carrito", example = "1")
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer cartId;

  @Schema(description = "Cantidad del producto", example = "3")
  @Getter @Setter private Integer amount;

  @Schema(description = "precio del producto en el carrito", example = "200.20")
  @Getter @Setter private double price;

  @Schema(description = "Estado del carrito abierto para agregar productos(false) o cerrado para evitar o cancelar el mismo(true)", example = "false")
  @Getter @Setter private boolean delivered;

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