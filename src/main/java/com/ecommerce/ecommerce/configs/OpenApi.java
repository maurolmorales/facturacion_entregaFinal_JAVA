package com.ecommerce.ecommerce.configs;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(
                title = "Ecommerce Entrega Final",
                version = "1.0",
                description = "Este proyecto es una aplicación de gestión de facturación y carrito de compras desarrollada con Java y Spring Boot. Permite a los usuarios agregar productos a un carrito, generar facturas y administrar la información de clientes, productos y facturas."
        )
)
public class OpenApi {


}
