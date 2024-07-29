package com.ecommerce.ecommerce.services;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
  @Autowired private ClientRepository clientRepository;

  public Client saveClient(@Valid Client client)throws Exception{
    if (client.getName() == ""){ throw new Exception("Customer name cannot be empty"); }
    if (client.getLastname() == ""){ throw new Exception("Customer lastname cannot be empty"); }
    if (client.getDocnumber() == null){ throw new Exception("Customer Document number cannot be empty"); }
    if (client.getEmail() == ""){ throw new Exception("Customer email cannot be empty"); }
    if (client.getPhone() == ""){ throw new Exception("Customer phone cannot be empty"); }
    return clientRepository.save(client);
  }

  public List<Client> findAllClient(){
    return clientRepository.findAll();
  }

  public Optional<Client> findOneClient(Integer id){
    return clientRepository.findById(id);
  }

  public Optional<Client> findByDocumentNumber(Integer docnumber){
    return clientRepository.findByDocnumber(docnumber);
  }

  public Client updateClientPartial (Integer clientID, Map<String, Object> updates) throws Exception {
    Optional<Client> clientFound = clientRepository.findById(clientID);
    if(clientFound.isEmpty()){ throw new Exception("Client Not Found with id: " + clientID); }
    else{
      Client client = clientFound.get();
      updates.forEach((key, value)->{
        switch(key){
          case "name":  client.setName((String) value);
            break;
          case "lastname": client.setLastname((String) value);
            break;
          case "docnumber": client.setDocnumber((Integer) value);
            break;
          case "email": client.setEmail((String) value);
            break;
          case "phone": client.setPhone((String) value);
            break;
        }
      });
      return clientRepository.save(client);
    }
  }

  public void deleteClient(Integer id){
    clientRepository.deleteById(id);
  }
}