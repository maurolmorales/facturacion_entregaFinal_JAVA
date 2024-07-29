package com.ecommerce.ecommerce.services;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
  @Autowired private ProductRepository productRepository;



  public Product saveProduct(@Valid Product product)throws Exception{
    if (product.getName() == ""){ throw new Exception("Customer name cannot be empty"); }
    if (product.getDescription() == ""){ throw new Exception("Customer description cannot be empty"); }
    if (product.getBrand() == null){ throw new Exception("Customer Document brand cannot be empty"); }
    if (product.getImageUrl() == ""){ throw new Exception("Customer imageUrl cannot be empty"); }
    if (product.getStock() == null){ throw new Exception("Customer stock cannot be empty"); }
    if (product.getPrice() == 0.00){ throw new Exception("Customer price cannot be empty"); }
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
          case "description": product.setDescription((String) value);
            break;
          case "brand": product.setBrand((String) value);
            break;
          case "imageUrl": product.setImageUrl((String) value);
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
