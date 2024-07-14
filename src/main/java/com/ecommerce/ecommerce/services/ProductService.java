package com.ecommerce.ecommerce.services;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
  @Autowired private ProductRepository productRepository;

  public Product saveProduct(Product product){
    return productRepository.save(product);
  }

  public List<Product> readAllProduct(){
    return productRepository.findAll();
  }

  public Optional<Product> readOneProduct(Integer id){
    return productRepository.findById(id);
  }

  public Product updatePartial (Integer productId, Map<String, Object> updates) throws Exception {
    Optional<Product> productFound = productRepository.findById(productId);
    if(productFound.isEmpty()){ throw new Exception("Product Not Found"); }
    else{
      Product product = productFound.get();
      updates.forEach((key, value)->{
        switch(key){
          case "name": product.setName((String) value);
            break;
          case "stock": product.setStock((Integer) value);
            break;
          case "price": product.setPrice((double) value);
            break;
        }
      });
      return productRepository.save(product);
    }
  }

  public void deleteOneProduct(Integer id){
    productRepository.deleteById(id);
  }
}
