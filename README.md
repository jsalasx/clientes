
# Clientes

# Version Java

##1.8.0_291

# Desarollo

Se logro realizar la funcionalidad de la aplicación.

Se adjunta colección de postman para las pruebas.

## https://github.com/jsalasx/clientes/blob/master/Apply_Salas_Alejandro_Sucasa.postman_collection.json

No se logro utilizar hsqldb, en su lugar se utilizo PostgreSQL.

# Crear la base de datos clientes en la interfaz de PGAdmin

![image](https://user-images.githubusercontent.com/102547359/223569717-22d1dca0-c563-4e38-b9bc-963405a1a83f.png)

# Modificar el archivo /src/main/resources/application.properties linea 5, 6 y 7 con sus credenciales

## spring.datasource.url=jdbc:postgresql://localhost:5432/clientes

## spring.datasource.username=SU_USUARIO

## spring.datasource.password=SU_CONTRASEÑA


# En la carpeta raiz ejecutar

## mvnw install

para compilar la aplicacion y el comando

## java -jar clientes-0.0.1-SNAPSHOT.jar

para ejecutar la aplicacion
