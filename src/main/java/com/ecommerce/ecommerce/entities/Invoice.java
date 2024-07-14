package com.ecommerce.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer invoiceId;
  @Getter @Setter private LocalDateTime create_at;
  @Getter @Setter private Double total;
  @Getter @Setter private String status;

  @OneToMany(mappedBy = "invoiceCart", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter @Setter private List<Cart> carts;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  @JsonIgnore
  @Getter @Setter private Client clientInvoice;
}