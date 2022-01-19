# Clothesstore

Aplicacion REST API spring boot, tiene varios servicios, como principales está  los servicios de buscar los productos mas vistos, agregar nuevo producto, actualizar un producto por la id, y como adicionales está el servicio de consultar todos los productos y consultar producto por id.

## ¿Cómo correr el proyecto?

Primero clonar el repositorio.

```bash
git clone https://github.com/juan-urrego/clothesShope.git
```

Una vez clonado, se importa el proyecto a tu IDE de preferencia, Intellij IDEA por preferencia


Para ejecutar los test unitarios y de integracion se ejecuta el comando:

```bash
mvn test
```
## URL Producción de la REST API

La Rest API está desplegada con Amazon Web Services(AWS) por medio de Elastic Beanstalk
[URL](http://clothes-env.eba-nrrb39ii.us-east-1.elasticbeanstalk.com/api/products/list)

## Documentación de la API

La documentación de la API puede ser accedida [documentacion](https://documenter.getpostman.com/view/10640966/UVXnGZUr).

## Estructura de base de datos
La aplicación usa bases de datos relacionales, como motor de base de datos se usó MySQL8, se hizo un simple diagrama de entidad relacion en el que podemos ver dos tablas: tabla products y tabla countries y a su vez, tienen una relacion de 1 a Muchos
Tabla Products:
El product tiene información relevante del producto, incluyendo sus dos imágenes requeridas, y una variable visitas como contador para que cada vez que alguien consulte ese producto en particular se autoincremente, esto para crear con facilidad el filtro que va a llamar los productos más vistos
En referente a las imágenes, están alojadas en AWS en S3, esto con el fin, para que se pueda acceder publicamente a la imagen, y poder ser mostrada en el frontend con facilidad.

Tabla Country:
Esta tabla nace por la necesidad de evaluar en qué pais el producto va a estar disponible, esto para que se hagan algunas restricciones referente al porcentaje de descuento, porque en algunas zonas el descuento máximo va a variar
y por defecto, se encuentran agregados los 4 paises mencionados en los requisitos, Existe un Endpoint para agregar otros paises por el codigo establecido en la norma ISO-3166 alfa-3
Paises agregados por defecto:
-COL
-PER
-MEX
-CHL

Algunas imágenes del proyecto

![alt text](https://clothesshop.s3.amazonaws.com/entityManagerFactory(EntityManagerFactoryBuilder).png)
![alt text](https://clothesshop.s3.amazonaws.com/arquitectura_despliegue-Page-3.drawio.png)
