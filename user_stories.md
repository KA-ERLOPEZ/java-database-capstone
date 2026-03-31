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


