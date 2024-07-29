package com.ecommerce.ecommerce.services;
import com.ecommerce.ecommerce.entities.Cart;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.entities.Invoice;
import com.ecommerce.ecommerce.repositories.CartRepository;
import com.ecommerce.ecommerce.repositories.ClientRepository;
import com.ecommerce.ecommerce.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InvoiceService {
  @Autowired private InvoiceRepository invoiceRepository;
  @Autowired private ClientRepository clientRepository;
  @Autowired private CartRepository cartRepository;



  public Invoice generateInvoice(Integer clientId) throws Exception{
    // Busca el cliente por su ID en el repositorio de clientes:
    Optional<Client> clientOptional = clientRepository.findById(clientId);

    // Verifica si el cliente existe y se obtiene la instancia del cliente encontrado:
    if(clientOptional.isEmpty()){ throw new Exception("Client Not Found with id: "+clientId);}
    Client clientFound = clientOptional.get();

    // Busca los ítems del carrito asociados al cliente encontrado y verifica si hay ítems en el carrito del cliente:
    List<Cart> cartItems = cartRepository.findByClientCart_ClientIdAndDeliveredFalse(clientId);
    if (cartItems.isEmpty()){ throw new Exception("No Items in cart for client with id: "+clientId); }

    // Calcula el total a pagar sumando el precio por la cantidad de cada ítem en el carrito:
    double total = cartItems.stream().mapToDouble(item -> item.getAmount() * item.getPrice()).sum();

    // Crea una nueva instancia de Invoice (factura):
    Invoice invoice = new Invoice();
    invoice.setClientInvoice(clientFound);
    invoice.setCreate_at(LocalDateTime.now());
    invoice.setTotal(total);
    invoice.setStatus("Paid");
    invoice = invoiceRepository.save(invoice);

    // Asigna la factura a cada ítem del carrito y guarda todos los ítems del carrito con la referencia a la factura:
    for (Cart cartItem : cartItems) {
      cartItem.setInvoiceCart(invoice);
      cartItem.setDelivered(true);
    }
    cartRepository.saveAll(cartItems);

    return invoice;
  }



  public Invoice getInvoicesByClientId(Integer clientId){
    List<Invoice> invoices = invoiceRepository.findLastByClientInvoice_ClientId(clientId);
    invoices.sort((o1, o2) -> o2.getCreate_at().compareTo(o1.getCreate_at()));
    return invoices.get(0);
  }



  public List<Invoice> readAllInvoices(){
    return invoiceRepository.findAll();
  }



  public Invoice updatePartial(Integer invoiceId, Map<String, Object> updates) throws Exception{
    Optional<Invoice> invoiceFound = invoiceRepository.findById(invoiceId);
    if(invoiceFound.isEmpty()){throw new Exception("Invoice not found");}
    else {
      Invoice invoice = invoiceFound.get();
      updates.forEach((key, value) -> {
        switch (key) {
          case "name":
            invoice.setCreate_at((LocalDateTime) value);
            break;
          case "stock":
            invoice.setTotal((double) value);
            break;
        }
      });
      return invoiceRepository.save(invoice);
    }
  }



  public void deleteOneInvoice(Integer id){
    invoiceRepository.deleteById(id);
  }
}