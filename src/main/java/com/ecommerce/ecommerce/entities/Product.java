package com.ecommerce.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "products") @NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Representa un producto")
public class Product {
  @Schema(description = "identificador único del producto", example = "1")
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer productId;

  @Schema(description = "Nombre del producto", example = "Chaqueta de Cuero Negra")
  @NotEmpty(message = "Name cannot be empty")
  @Getter @Setter private String name;

  @Schema(description = "Descripción del producto", example = "Chaqueta de cuero genuino en color negro con cierre frontal y bolsillos laterales.")
  @NotEmpty(message = "Description cannot be empty")
  @Getter @Setter private String description;

  @Schema(description = "Marca del producto", example = "LeatherLux")
  @NotEmpty(message = "brand cannot be empty")
  @Getter @Setter private String brand;

  @Schema(description = "Imagen del producto", example = "https://example.com/images/chaqueta-cuero-negra.jpg")
  @NotEmpty(message = "imageUrl cannot be empty")
  @Getter @Setter private String imageUrl;

  @Schema(description = "Stock del producto", example = "25")
  @NotEmpty(message = "Stock cannot be empty")
  @Getter @Setter private Integer stock;

  @Schema(description = "Precio del producto", example = "525,50")
  @NotEmpty(message = "Price cannot be empty")
  @Getter @Setter private double price;

  @OneToMany(mappedBy = "productCart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonIgnore
  @Getter @Setter private List<Cart> carts;
}