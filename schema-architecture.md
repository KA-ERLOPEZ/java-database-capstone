Esta aplicación de Spring Boot utiliza tanto controladores MVC como REST. 
Se utilizan plantillas de Thymeleaf para los paneles de administración y de doctor, mientras que las API REST sirven a todos los demás módulos. 
La aplicación interactúa con dos bases de datos: MySQL (para datos de pacientes, doctores, citas y administración) y MongoDB (para recetas). 
Todos los controladores dirigen las solicitudes a través de una capa de servicio común, que a su vez delega en los repositorios apropiados. 
MySQL utiliza entidades JPA mientras que MongoDB utiliza modelos de documentos.

Flujos numerados de datos y control
El usuario accede al Admin Dashboard o al panel de citas desde la interfaz.
La solicitud es manejada por el controlador (ya sea Thymeleaf para vistas o REST para APIs).
El controlador delega la lógica a la capa de servicios (service layer).
La capa de servicios procesa la lógica de negocio, aplicando reglas y validaciones, y se comunica con la capa de repositorio.
La capa de repositorio interactúa con la base de datos, realizando consultas y operaciones.
La base de datos devuelve los datos, que son mapeados a entidades o modelos.
Los modelos transportan los datos de vuelta a través de las capas hasta el controlador.
