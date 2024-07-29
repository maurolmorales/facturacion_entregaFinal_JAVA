package com.ecommerce.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "clients") @NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Represanta un cliente")
public class Client {
  @Schema(description = "identificador único del cliente", example = "1")
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Integer clientId;

  @Schema(description = "Nombre del cliente", example = "juan")
  @NotEmpty(message = "Name cannot be empty")
  @Getter @Setter private String name;

  @Schema(description = "Apellido del cliente", example = "Doe")
  @NotEmpty(message = "lastname cannot be empty")
  @Getter @Setter private String lastname;

  @Schema(description = "Número de documento del cliente", example = "12345678")
  @NotEmpty(message = "Document number cannot be null")
  @Getter @Setter private Integer docnumber;

  @Schema(description = "Correo electrónico del cliente", example = "juan@email.com")
  @NotEmpty(message = "Email cannot be empty")
  @Email(message = "Email should be valid")
  @Getter @Setter private String email;

  @Schema(description = "Teléfono del cliente", example = "15-1234-5678")
  @NotEmpty(message = "Phone cannot be empty")
  @Getter @Setter private String phone;

  @OneToMany(mappedBy = "clientInvoice", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore @Getter @Setter private List<Invoice> invoices;

  @OneToMany(mappedBy = "clientCart", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore @Getter @Setter private List<Cart> carts;
}