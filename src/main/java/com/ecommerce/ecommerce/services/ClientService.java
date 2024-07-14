package com.ecommerce.ecommerce.services;
import com.ecommerce.ecommerce.entities.Client;
import com.ecommerce.ecommerce.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
  @Autowired private ClientRepository clientRepository;

  public Client saveClient(Client client){
    return clientRepository.save(client);
  }

  public List<Client> findAllClient(){
    return clientRepository.findAll();
  }

  public Optional<Client> findOneClient(Integer id){
    return clientRepository.findById(id);
  }

  public Client updateClientPartial (Integer clientID, Map<String, Object> updates) throws Exception {
    Optional<Client> clientFound = clientRepository.findById(clientID);
    if(clientFound.isEmpty()){ throw new Exception("Client Not Found"); }
    else{
      Client client = clientFound.get();
      updates.forEach((key, value)->{
        switch(key){
          case "name": client.setName((String) value);
            break;
          case "lastname": client.setLastname((String) value);
            break;
          case "docnumber": client.setDocnumber((Integer) value);
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
