# Historias de Usuario - Administrador

---

## 1. Inicio de sesión

**Título:**
_Como administrador, quiero iniciar sesión en el portal con mi nombre de usuario y contraseña, para que pueda gestionar la plataforma de manera segura._

**Criterios de Aceptación:**
1. El sistema debe permitir ingresar usuario y contraseña.
2. El sistema debe validar las credenciales.
3. El administrador accede al panel si las credenciales son correctas.

**Prioridad:** Alta  
**Puntos de Historia:** 3  

**Notas:**
- Validar intentos fallidos de login.

---

## 2. Cierre de sesión

**Título:**
_Como administrador, quiero cerrar sesión en el portal, para proteger el acceso al sistema._

**Criterios de Aceptación:**
1. El sistema debe permitir cerrar sesión desde el panel.
2. La sesión debe invalidarse correctamente.
3. El usuario debe ser redirigido al login.

**Prioridad:** Alta  
**Puntos de Historia:** 2  

**Notas:**
- Manejar expiración de sesión por inactividad.

---

## 3. Agregar doctores

**Título:**
_Como administrador, quiero agregar doctores al portal, para gestionar los profesionales disponibles en el sistema._

**Criterios de Aceptación:**
1. El sistema debe permitir ingresar datos del doctor.
2. Los datos deben validarse antes de guardarse.
3. El doctor debe registrarse correctamente en la base de datos.

**Prioridad:** Alta  
**Puntos de Historia:** 5  

**Notas:**
- Validar duplicados (correo o identificación).

---

## 4. Eliminar doctor

**Título:**
_Como administrador, quiero eliminar el perfil de un doctor del portal, para mantener actualizada la información del sistema._

**Criterios de Aceptación:**
1. El sistema debe permitir seleccionar un doctor.
2. Debe solicitar confirmación antes de eliminar.
3. El registro debe eliminarse correctamente.

**Prioridad:** Media  
**Puntos de Historia:** 3  

**Notas:**
- Considerar eliminación lógica (soft delete).

---

## 5. Estadísticas de citas

**Título:**
_Como administrador, quiero ejecutar un procedimiento almacenado en MySQL para obtener el número de citas por mes, para analizar y rastrear las estadísticas de uso._

**Criterios de Aceptación:**
1. El sistema debe ejecutar el procedimiento almacenado.
2. Debe recuperar correctamente los datos por mes.
3. Los resultados deben mostrarse de forma clara.

**Prioridad:** Media  
**Puntos de Historia:** 5  

**Notas:**
- Evaluar visualización mediante gráficos.

# Historias de Usuario - Paciente

---

## 1. Ver lista de doctores

**Título:**
_Como paciente, quiero ver una lista de doctores sin iniciar sesión, para explorar opciones antes de registrarme._

**Criterios de Aceptación:**
1. El sistema debe mostrar la lista de doctores sin autenticación.
2. La información básica de cada doctor debe ser visible.
3. La lista debe cargarse correctamente.

**Prioridad:** Alta  
**Puntos de Historia:** 3  

**Notas:**
- Mostrar especialidad y disponibilidad básica.

---

## 2. Registro de usuario

**Título:**
_Como paciente, quiero registrarme usando mi correo electrónico y contraseña, para poder reservar citas._

**Criterios de Aceptación:**
1. El sistema debe permitir ingresar correo y contraseña.
2. Debe validar el formato del correo.
3. El usuario debe registrarse correctamente.

**Prioridad:** Alta  
**Puntos de Historia:** 5  

**Notas:**
- Validar correos duplicados.

---

## 3. Inicio de sesión

**Título:**
_Como paciente, quiero iniciar sesión en el portal, para gestionar mis reservas._

**Criterios de Aceptación:**
1. El sistema debe permitir ingresar credenciales.
2. Debe validar usuario y contraseña.
3. Debe redirigir al panel del paciente.

**Prioridad:** Alta  
**Puntos de Historia:** 3  

**Notas:**
- Manejar errores de autenticación.

---

## 4. Cierre de sesión

**Título:**
_Como paciente, quiero cerrar sesión en el portal, para asegurar mi cuenta._

**Criterios de Aceptación:**
1. El sistema debe permitir cerrar sesión.
2. Debe invalidar la sesión activa.
3. Debe redirigir al login.

**Prioridad:** Alta  
**Puntos de Historia:** 2  

**Notas:**
- Implementar expiración automática por inactividad.

---

## 5. Reservar cita

**Título:**
_Como paciente, quiero iniciar sesión y reservar una cita de una hora con un doctor, para recibir atención médica._

**Criterios de Aceptación:**
1. El usuario debe estar autenticado.
2. Debe poder seleccionar doctor, fecha y hora.
3. La cita debe guardarse correctamente.

**Prioridad:** Alta  
**Puntos de Historia:** 5  

**Notas:**
- Validar disponibilidad del doctor.

---

## 6. Ver próximas citas

**Título:**
_Como paciente, quiero ver mis próximas citas, para poder prepararme adecuadamente._

**Criterios de Aceptación:**
1. El sistema debe mostrar las citas futuras del paciente.
2. La información debe incluir fecha, hora y doctor.
3. Los datos deben actualizarse correctamente.

**Prioridad:** Media  
**Puntos de Historia:** 3  

**Notas:**
- Posible integración con recordatorios.

