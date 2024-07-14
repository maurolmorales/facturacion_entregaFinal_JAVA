package com.ecommerce.ecommerce.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Client {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer clientId;
  @Getter @Setter private String name;
  @Getter @Setter private String lastname;
  @Getter @Setter private Integer docnumber;

  @OneToMany(mappedBy = "clientInvoice", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter @Setter private List<Invoice> invoices;

  @OneToMany(mappedBy = "clientCart", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter @Setter private List<Cart> carts;
}