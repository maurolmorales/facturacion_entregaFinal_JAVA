package com.ecommerce.ecommerce.controllers;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.entities.Invoice;
import com.ecommerce.ecommerce.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {
  @Autowired private InvoiceService invoiceService;

  @PostMapping
  public ResponseEntity<Invoice> generateInvoice(@RequestBody Client clientId){
    try{
      return new ResponseEntity<>(invoiceService.generateInvoice(clientId), HttpStatus.CREATED);
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Invoice>> getAllInvoices(){
    try{
      List<Invoice> invoice = invoiceService.readAllInvoices();
      if(!invoice.isEmpty()){ return ResponseEntity.ok(invoice); }
      else{ return ResponseEntity.noContent().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneInvoice(@PathVariable Integer id){
    try{
      Optional<Invoice> invoice = invoiceService.findOneInvoice(id);
      if(!invoice.isEmpty()){ return ResponseEntity.ok(invoice.get()); }
      else{ return ResponseEntity.notFound().build(); }
    }catch (Exception error){
      System.out.println(error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}