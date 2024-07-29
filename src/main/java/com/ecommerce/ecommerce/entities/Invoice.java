package com.ecommerce.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice") @NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Represanta un Comprobante")
public class Invoice {
  @Schema(description = "identificador único del comprobante", example = "1")
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer invoiceId;

  @Schema(description = "Fecha y hora de la creacion del comprobante", example = "2024-07-26T19:56:46.356448")
  @Getter @Setter private LocalDateTime create_at;

  @Schema(description = "Saldo total de la compra", example = "730.50")
  @Getter @Setter private Double total;

  @Schema(description = "Controla el flujo de la factura", example = "Paid")
  @Getter @Setter private String status;

  @OneToMany(mappedBy = "invoiceCart", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Getter @Setter private List<Cart> carts;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  @JsonIgnore
  @Getter @Setter private Client clientInvoice;
}