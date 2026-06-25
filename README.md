# Feminist Library

## Descripción del proyecto

**Feminist Library** es una aplicación desarrollada en **Java** para gestionar el inventario de una biblioteca feminista de barrio.

El objetivo principal del proyecto es modernizar la organización de los libros mediante un sistema que permita registrar, consultar, actualizar, eliminar y buscar libros de forma sencilla desde la terminal.

La aplicación permite mantener la información de la biblioteca estructurada y actualizada en una base de datos **PostgreSQL**, facilitando la gestión diaria de libros, autoras, editoriales, géneros y formatos.

---

## Objetivo

La biblioteca feminista necesita disponer de un sistema que permita gestionar sus libros de manera eficiente.

Con esta aplicación, la administradora podrá:

- Añadir nuevos libros al inventario.
- Consultar todos los libros registrados.
- Buscar libros por título.
- Actualizar información existente.
- Eliminar libros del sistema.
- Gestionar autoras, editoriales, géneros y formatos.
- Mantener una base de datos organizada y actualizada.

---

## Tecnologías utilizadas

- Java
- PostgreSQL
- Maven
- JDBC
- Git
- GitHub
- Variables de entorno con archivo `.env`

---

## Arquitectura del proyecto

El proyecto está organizado siguiendo una estructura basada en el patrón **MVC** y el patrón **Repository**.

Esta organización permite separar responsabilidades, mantener el código más limpio y facilitar el mantenimiento del proyecto.

---

## Estructura del proyecto

```text
FeministLibrary/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── femcoders/
│                   ├── config/
│                   │   └── DBManager.java
│                   │
│                   ├── controller/
│                   │   └── BookController.java
│                   │
│                   ├── model/
│                   │   ├── Author.java
│                   │   ├── Book.java
│                   │   ├── Format.java
│                   │   ├── Genre.java
│                   │   └── Publisher.java
│                   │
│                   ├── repository/
│                   │   ├── AuthorRepository.java
│                   │   ├── AuthorRepositoryImpl.java
│                   │   ├── BookRepository.java
│                   │   ├── BookRepositoryImpl.java
│                   │   ├── GenreRepository.java
│                   │   ├── GenreRepositoryImpl.java
│                   │   ├── PublisherRepository.java
│                   │   └── PublisherRepositoryImpl.java
│                   │
│                   ├── view/
│                   │   ├── BookView.java
│                   │   ├── Colors.java
│                   │   └── MenuView.java
│                   │
│                   └── Main.java
│
├── .env.example
├── .gitignore
└── pom.xml
```

---

## Capas del proyecto

### Config

Contiene la configuración de conexión con la base de datos.

Archivo principal:

```text
DBManager.java
```

Esta clase se encarga de gestionar la conexión con PostgreSQL utilizando los datos definidos en las variables de entorno.

---

### Model

Contiene las clases que representan las entidades principales del sistema.

Entidades principales:

- `Book`
- `Author`
- `Genre`
- `Publisher`
- `Format`

Estas clases representan la información que se almacena y se gestiona dentro de la aplicación.

---

### Repository

Contiene las interfaces y sus implementaciones para acceder a los datos.

Esta capa se encarga de realizar las operaciones con la base de datos, separando la lógica de persistencia del resto de la aplicación.

Ejemplos:

```text
BookRepository.java
BookRepositoryImpl.java
AuthorRepository.java
AuthorRepositoryImpl.java
GenreRepository.java
GenreRepositoryImpl.java
PublisherRepository.java
PublisherRepositoryImpl.java
```

---

### Controller

Contiene la lógica que conecta la vista con los repositorios.

Archivo principal:

```text
BookController.java
```

El controlador recibe las acciones del usuario desde la vista y coordina las operaciones necesarias con la capa de repositorio.

---

### View

Contiene la parte visual de la aplicación, implementada mediante terminal.

Archivos principales:

```text
BookView.java
Colors.java
MenuView.java
```

- `BookView.java`: gestiona la interacción con el usuario para las operaciones relacionadas con libros.
- `MenuView.java`: muestra el menú principal de la aplicación.
- `Colors.java`: permite aplicar colores en la consola para mejorar la experiencia visual del usuario.

---

### Main

Archivo principal de ejecución de la aplicación.

```text
Main.java
```

Es el punto de entrada del programa.

---

## Funcionalidades principales

### Gestión de libros

- Crear libros.
- Listar todos los libros.
- Buscar libros por título.
- Actualizar libros existentes.
- Eliminar libros por identificador.

---

### Gestión de autoras

- Crear autoras.
- Consultar autoras existentes.
- Relacionar autoras con libros.

---

### Gestión de editoriales

- Crear editoriales.
- Consultar editoriales existentes.
- Relacionar editoriales con libros.

---

### Gestión de géneros

- Crear géneros.
- Evitar duplicados.
- Relacionar libros con uno o varios géneros mediante una tabla intermedia.

---

### Validaciones

El proyecto incluye validaciones para mejorar la calidad de los datos introducidos por el usuario, como por ejemplo:

- Validación de ISBN.
- Validación de campos obligatorios.
- Control de datos duplicados.
- Comprobación de datos antes de insertar o actualizar registros.

---

## Base de datos

El proyecto utiliza **PostgreSQL** como sistema de base de datos relacional.

La aplicación se conecta a la base de datos mediante JDBC.

La configuración de la conexión se gestiona desde la clase:

```text
src/main/java/com/femcoders/config/DBManager.java
```

---

## Gestión de contraseñas y variables de entorno

Para proteger los datos sensibles, el proyecto utiliza un archivo de variables de entorno.

Las credenciales de la base de datos, como el usuario y la contraseña, no deben escribirse directamente en el código ni compartirse en GitHub.

Por este motivo, el proyecto incluye un archivo de ejemplo:

```text
.env.example
```

Este archivo sirve como plantilla para que cada persona del equipo cree su propio archivo `.env`.

---

## Archivo `.env.example`

Ejemplo de contenido:

```env
DB_URL=jdbc:postgresql://localhost:5432/feminist_library
DB_USER=your_user
DB_PASSWORD=your_password
```

Cada persona debe crear un archivo llamado `.env` en su entorno local y completar sus propios datos:

```env
DB_URL=jdbc:postgresql://localhost:5432/feminist_library
DB_USER=postgres
DB_PASSWORD=mi_contraseña_local
```

---

## Importante sobre el archivo `.env`

El archivo `.env` contiene información privada y no debe subirse al repositorio.

Por seguridad, debe estar incluido en el archivo `.gitignore`.

Ejemplo:

```gitignore
.env
target/
.idea/
.vscode/
*.class
```

De esta forma, cada persona puede trabajar con sus propias credenciales sin exponer contraseñas ni datos sensibles en GitHub.

---

## Requisitos previos

Antes de ejecutar el proyecto, es necesario tener instalado:

- Java
- Maven
- PostgreSQL
- Git

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

---

### 2. Entrar en el proyecto

```bash
cd FeministLibrary
```

---

### 3. Crear la base de datos

Crear una base de datos en PostgreSQL.

Ejemplo:

```sql
CREATE DATABASE feminist_library;
```

---

### 4. Crear el archivo `.env`

Copiar el archivo `.env.example` y crear un nuevo archivo llamado `.env`.

```bash
cp .env.example .env
```

Después, editar el archivo `.env` con los datos reales de conexión a la base de datos.

Ejemplo:

```env
DB_URL=jdbc:postgresql://localhost:5432/feminist_library
DB_USER=postgres
DB_PASSWORD=mi_contraseña
```

---

### 5. Compilar el proyecto

```bash
mvn clean install
```

---

### 6. Ejecutar la aplicación

```bash
mvn exec:java
```

También se puede ejecutar directamente desde la clase:

```text
Main.java
```

---

## Flujo de trabajo con Git

El equipo trabaja utilizando ramas para organizar las funcionalidades.

Ejemplos de ramas:

```text
dev
feat/create-book
feat/read-all-books
feat/update-book
feat/delete-book
feat/create-author
feat/create-publisher
```

Los cambios se desarrollan en ramas individuales o de funcionalidad y posteriormente se integran mediante Pull Request.

El flujo habitual es:

1. Crear una rama desde `dev`.
2. Desarrollar la funcionalidad.
3. Hacer commit de los cambios.
4. Subir la rama al repositorio remoto.
5. Crear un Pull Request.
6. Revisar y hacer merge cuando el código funcione correctamente.

---

## Estado actual del proyecto

Actualmente el proyecto incluye:

- Estructura Maven organizada.
- Conexión con PostgreSQL.
- Uso de archivo `.env` para proteger credenciales.
- Modelos principales creados.
- Repositorios para libros, autoras, géneros y editoriales.
- Controlador de libros.
- Vista por terminal.
- Menú principal.
- Colores en consola.
- Funcionalidad para leer todos los libros.
- Validación de ISBN.
- Tabla intermedia de géneros para evitar duplicados.
- Trabajo colaborativo con ramas y Pull Requests.

---

## Próximas mejoras

- Mejorar la búsqueda por título para que no distinga entre mayúsculas y minúsculas.
- Completar todas las operaciones CRUD.
- Añadir tests unitarios.
- Mejorar la gestión de errores.
- Mejorar los mensajes mostrados en la terminal.
- Documentar el modelo de base de datos.
- Añadir más validaciones para los datos introducidos por el usuario.
- Mejorar la experiencia de uso del menú principal.

---

## Buenas prácticas aplicadas

- Separación de responsabilidades.
- Uso del patrón MVC.
- Uso del patrón Repository.
- Uso de interfaces para desacoplar la lógica de acceso a datos.
- Organización del código por paquetes.
- Protección de credenciales mediante `.env`.
- Uso de `.env.example` como plantilla segura.
- Uso de `.gitignore` para evitar compartir archivos sensibles.
- Control de versiones con Git.
- Trabajo colaborativo mediante ramas y Pull Requests.

---

## Equipo de desarrollo

Proyecto desarrollado como parte de una práctica formativa de backend con Java y PostgreSQL.

Integrantes:

- Fabiana
- Aïda
- Rose
- Damaris

---

## Licencia

Este proyecto ha sido desarrollado con fines educativos.
