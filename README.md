# Contac Web

**Contac** es una aplicación web full-stack para la creación y gestión de temas de debate con comentarios públicos. Integra vistas modernas con Bootstrap y un backend REST seguro con autenticación JWT.

## 📋 Tabla de Contenidos
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Instalación](#-instalación)
- [Configuración](#️-configuración)
- [Uso](#-uso)
- [API Reference](#-api-reference)
- [Pruebas](#-pruebas)
- [Autor](#-autor)

##  Características

-  Autenticación y autorización con JWT
-  Creación y gestión de temas de debate
-  Sistema de comentarios públicos
-  Búsqueda de temas por título
-  Soporte para contenido mediante URL o archivo
-  Interfaz responsiva con Bootstrap

## 🛠️ Tecnologías

### Frontend
- HTML5,
- CSS3,
- JavaScript

### Backend
- Java 21
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Tokens)
- Spring Data JPA
- Base de datos (especificar: MySQL)

## 🚀 Instalación

### Prerrequisitos
```bash
- Java 17 o superior
- Maven 3.8+
- Base de datos (MySQL)
```

### Pasos
```bash
# Clonar el repositorio
git clone https://github.com/EMILIO25CC/contac-web.git

# Navegar al directorio
cd contac-web

# Configurar base de datos en application.properties

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

## ⚙️ Configuración

### URLs del proyecto
```
Web:  http://localhost:8080
API:  http://localhost:8080/api
```

### Variables de entorno (application.properties)
```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/contac_db
spring.datasource.username=root
spring.datasource.password=password

# JWT
jwt.secret=tu_clave_secreta_aqui
jwt.expiration=86400000
```

## 📖 Uso

### 1. Acceder a la aplicación
Abre tu navegador en `http://localhost:8080`

### 2. Registro/Login
Crea una cuenta o inicia sesión con:
- **Usuario:** admin
- **Password:** Admin123!

### 3. Crear temas
Una vez autenticado, puedes crear nuevos temas de debate mediante URL o subiendo archivos.

### 4. Comentar
Cualquier usuario puede comentar en los temas sin necesidad de autenticación.

## 📡 API Reference

### Autenticación

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "Admin123!"
}
```

**Respuesta exitosa:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin"
}
```

### Temas

#### Endpoints públicos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/temas/listar` | Lista todos los temas |
| GET | `/api/temas/ver/{id}` | Obtiene un tema específico |
| GET | `/api/temas/buscar?titulo={texto}` | Busca temas por título |
| GET | `/api/temas/archivo/{id}` | Descarga archivo de un tema |

#### Endpoints protegidos (requieren JWT)

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/api/temas/url` | Crear tema con URL | ✅ |
| POST | `/api/temas/archivo` | Crear tema con archivo | ✅ |
| PUT | `/api/temas/url/{id}` | Actualizar tema (URL) | ✅ |
| PUT | `/api/temas/archivo/{id}` | Actualizar tema (archivo) | ✅ |
| DELETE | `/api/temas/{id}` | Eliminar tema | ✅ |

**Header requerido para endpoints protegidos:**
```
Authorization: Bearer {TOKEN}
```

#### Ejemplo: Crear tema con URL
```http
POST /api/temas/url
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "titulo": "Debate sobre IA",
  "descripcion": "Discutamos el futuro de la inteligencia artificial",
  "url": "https://example.com/ia-debate"
}
```

### Comentarios

#### Endpoints públicos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/comentarios` | Crear comentario |
| GET | `/api/comentarios/tema/{idTema}` | Listar comentarios de un tema |

#### Ejemplo: Crear comentario
```http
POST /api/comentarios
Content-Type: application/json

{
  "temaId": 1,
  "autor": "Juan",
  "contenido": "Excelente tema de debate"
}
```

## 🧪 Pruebas

### Con Postman
1. Importa la colección desde `/postman/Contac-API.postman_collection.json`
2. Configura la variable de entorno `base_url` a `http://localhost:8080`
3. Ejecuta las pruebas en orden:
   - Login → guarda el token
   - Crear tema
   - Listar temas
   - Crear comentario

### Tests unitarios
```bash
mvn test
```

## 📝 Notas importantes

-  Los comentarios son públicos y no requieren autenticación
-  Los endpoints de gestión de temas requieren JWT válido
-  Los temas pueden incluir contenido mediante URL externa o archivo adjunto
-  El token JWT expira después de 24 horas (configurable)

## Troubleshooting

### Error: "Token inválido"
Verifica que el token JWT esté correctamente formateado en el header `Authorization: Bearer {TOKEN}`

### Error: "Conexión rechazada"
Asegúrate de que la base de datos esté corriendo y las credenciales sean correctas

## Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👤 Autor

**Emilio**
- GitHub: [@EMILIO25CC](https://github.com/EMILIO25CC)
- LinkedIn: [Tu LinkedIn] (opcional)
- Email: [Tu email] (opcional)

## 🙏 Agradecimientos

- Spring Boot Team
- Bootstrap
- Comunidad de desarrolladores

---

⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub
