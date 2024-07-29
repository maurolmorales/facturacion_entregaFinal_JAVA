package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Invoice;
import com.ecommerce.ecommerce.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("api/v1/invoices")
@Tag(name = "Ruta de Comprobantes", description = "Una API para gestionar los compronantes")
public class InvoiceController {
  @Autowired private InvoiceService invoiceService;


  @PostMapping("/{clientId}")
  @Operation(summary = "Genera un comprobante para los productos en el carrito del cliente",
    description = "Genera un comprobante con el total a pagar por un cliente por sus productos en el carrito (no entregados), y marca esos productos como entregados.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Comprobante creado exitosamente.",
      content = @Content(schema = @Schema(implementation = Invoice.class))),
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado o no hay productos en el carrito.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Cliente no encontrado o no hay productos en el carrito.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor.\" }")))
  })
  public ResponseEntity<Object> generateInvoice(@PathVariable Integer clientId){
    try{
      Invoice invoiceGenerate = invoiceService.generateInvoice(clientId);
      return ResponseEntity.status(HttpStatus.CREATED).body(invoiceGenerate);
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error."));
    }
  }



  @GetMapping("/{clientId}")
  @Operation(summary = "Obtiene un comprobante por el ID", description = "Recupera un comprobante específico por su ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Comprobante encontrado exitosamente.",
      content = @Content(schema = @Schema(implementation = Invoice.class))),
    @ApiResponse(responseCode = "404", description = "Comprobante no encontrado o no hay productos en el carrito.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Comprobante no encontrado o no hay productos en el carrito.\" }"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
      content = @Content(mediaType = "application/json",
      examples = @ExampleObject(value = "{ \"message\": \"Error interno del servidor.\" }")))
  })
  public ResponseEntity<Object> getInvoicesByClientId(@PathVariable Integer clientId){
    try{
      Invoice invoice = invoiceService.getInvoicesByClientId(clientId);
      return ResponseEntity.ok(invoice);

    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Internal Server Error."));
    }
  }

}