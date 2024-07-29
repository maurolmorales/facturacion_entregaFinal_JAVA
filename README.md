# Facturación y Carrito de Compras

---

Este proyecto es una aplicación de gestión de facturación y carrito de compras desarrollada con Java y Spring Boot. Permite a los usuarios agregar productos a un carrito, generar facturas y administrar la información de clientes, productos y facturas.

Documentación Swagger: [LINK](http://localhost:8080/swagger-ui.html)

## Descripción
El proyecto está compuesto por las siguientes entidades:

1. Client: Representa a un cliente y contiene información como ID, nombre, apellido y número de documento.
2. Product: Representa un producto y contiene información como ID, nombre, stock y precio.
3. Invoice: Representa una factura y contiene información como ID, fecha de creación, total y estado.
4. Cart: Representa un ítem en el carrito de compras y contiene información como ID, cantidad, precio, cliente, producto y factura.

## Instalación

Para clonar y ejecutar esta aplicación en tu máquina local, sigue estos pasos:

1. Clona el repositorio:

```bash
git clone https://github.com/maurolmorales/facturacion-segundaEntrega-JAVA.git
```

2. Configurar la Base de Datos:

```
spring.datasource.url=jdbc:mysql://localhost:3306/facturacion
spring.datasource.username=[tu_usuario]
spring.datasource.password=[tu_contraseña]
```

Asegúrate de reemplazar nombre_basedatos, tu_usuario y tu_contraseña con los valores correspondientes de tu configuración de MySQL.


3. Ejecutar la Aplicación:

En tu IDE, busca la clase principal (generalmente anotada con @SpringBootApplication) y ejecútala como una aplicación Java.

4. Probar la API con Postman:

Agregar un Producto:

- URL: "http://localhost:8080/api/v1/products"
- BODY:
```
{
"name": "Campera",
"stock": 4,
"price": 135.35
}
```

Obtener Todos los Productos:
- Método: GET
- URL: http://localhost:8080/api/v1/products


Obtener un productos en concreto:
- Método: GET
- URL: http://localhost:8080/api/v1/{productId}

Modificar un producto en concreto:
- Método: PATCH
- URL: "http://localhost:8080/api/v1/products/5"
- BODY:
```
{
"stock": 150,
}
```
Agregar ingresar un cliente:
- Método: POST
- URL: "http://localhost:8080/api/v1/auth/register"
- BODY:
```
{
"name": "nombre_cliente",
"lastname": "apellido_cliente,
"docnumber": 23456789
}
```

Modificar un cliente en concreto:
- Método: PATCH
- URL: "http://localhost:8080/api/v1/auth/me/5"
- BODY:
```
{
"name": "Fernanda",
}
```

Agregar Ítem al Carrito:
- Método: PUT
- URL: "http://localhost:8080/api/v1/{clientId}/{productId}/{amount}"

Eliminar un producto de un carrito:
- Método: DELETE
- URL: "http://localhost:8080/api/v1/carts/{cartId}"

Obtener Todos los Ítems del Carrito:
- Método: GET
- URL: http://localhost:8080/api/v1/carts


Obtener un factura en particular:
- Método: GET
- URL: http://localhost:8080/api/v1/invoice/{invoiceID}

Generar una factura:
- Método: POST
- URL: http://localhost:8080/api/v1/invoices
- BODY:
```
{
"clientId": 2,
}
```