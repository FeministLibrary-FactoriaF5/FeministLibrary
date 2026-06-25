# Feminist Library 📚

## Descripción del proyecto

**Feminist Library** es una aplicación desarrollada en **Java** para gestionar el inventario de una biblioteca feminista de barrio.

El objetivo principal del proyecto es modernizar la organización de los libros mediante un sistema que permita registrar, consultar, actualizar, eliminar y buscar libros de forma sencilla desde la terminal.

La aplicación mantiene la información estructurada y actualizada en una base de datos **PostgreSQL**, facilitando la gestión diaria de libros, autoras, editoriales, géneros y formatos.

---

## Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 25 | Lenguaje principal |
| PostgreSQL | 16+ | Base de datos relacional |
| Maven | 3.x | Gestión de dependencias y build |
| JDBC | - | Conexión con la base de datos |
| JUnit 5 | 5.11.0 | Tests unitarios |
| Mockito | 5.23.0 | Mocks para tests |
| dotenv-java | 3.2.0 | Gestión de variables de entorno |
| Git / GitHub | - | Control de versiones |

---

## Arquitectura del proyecto

El proyecto sigue el patrón **MVC (Model - View - Controller)** combinado con el patrón **Repository**, lo que permite separar responsabilidades y facilitar el mantenimiento y las pruebas del código.

---

## Estructura del proyecto

```text
FeministLibrary/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── femcoders/
│   │               ├── config/
│   │               │   └── DBManager.java
│   │               ├── controller/
│   │               │   └── BookController.java
│   │               ├── model/
│   │               │   ├── Author.java
│   │               │   ├── Book.java
│   │               │   ├── Format.java
│   │               │   ├── Genre.java
│   │               │   └── Publisher.java
│   │               ├── repository/
│   │               │   ├── AuthorRepository.java
│   │               │   ├── AuthorRepositoryImpl.java
│   │               │   ├── BookRepository.java
│   │               │   ├── BookRepositoryImpl.java
│   │               │   ├── GenreRepository.java
│   │               │   ├── GenreRepositoryImpl.java
│   │               │   ├── PublisherRepository.java
│   │               │   └── PublisherRepositoryImpl.java
│   │               ├── view/
│   │               │   ├── BookView.java
│   │               │   ├── Colors.java
│   │               │   └── MenuView.java
│   │               └── Main.java
│   └── test/
│       └── java/
│           └── com/femcoders/
│               └── repository/
│                   └── BookRepositoryTest.java
├── .env.example
├── .gitignore
├── schema.sql
└── pom.xml
```

---

## Capas del proyecto

### Config

Gestiona la conexión con la base de datos PostgreSQL leyendo las credenciales desde el archivo `.env`.

### Model

Representa las entidades del sistema: `Book`, `Author`, `Genre`, `Publisher` y `Format` (enum con los valores `paperback`, `hardcover`, `ebook`, `audiobook`).

### Repository

Interfaces e implementaciones para el acceso a datos mediante JDBC. Cada entidad tiene su propia interfaz y su implementación:

- `BookRepository` / `BookRepositoryImpl`
- `AuthorRepository` / `AuthorRepositoryImpl`
- `GenreRepository` / `GenreRepositoryImpl`
- `PublisherRepository` / `PublisherRepositoryImpl`

### Controller

`BookController` coordina la lógica entre la vista y los repositorios. Recibe las acciones del usuario, valida los datos y delega las operaciones en la capa de repositorio.

### View

Interfaz de usuario por terminal:

- `MenuView.java` — menú principal con navegación por opciones numéricas
- `BookView.java` — interacción con el usuario para todas las operaciones de libros
- `Colors.java` — constantes ANSI para colorear la terminal

---

## Modelo de base de datos

El modelo está normalizado hasta la **3FN (Tercera Forma Normal)**.

```sql
books       (id, title, author_id FK, publisher_id FK, isbn UNIQUE, published_year, summary, format)
authors     (id, name)
publishers  (id, name)
genres      (id, name)
genre_book  (book_id FK, genre_id FK)   ← tabla puente N:M
```

### Reglas de borrado (ON DELETE)

| Relación | Comportamiento |
|---|---|
| Borrar autor | Se borran sus libros en cascada (`CASCADE`) |
| Borrar editorial | El libro queda sin editorial (`SET NULL`) |
| Borrar género | Se elimina solo la relación libro-género (`CASCADE` en tabla puente) |
| Borrar libro | Se eliminan sus relaciones de género automáticamente (`CASCADE`) |

---

## Funcionalidades implementadas (CRUD)

### Libros ✅
- ➕ Crear libro (con validación de ISBN y detección de duplicados)
- 📖 Ver todos los libros
- 🔍 Buscar por título
- 🔍 Buscar por ID
- 🗑️ Eliminar libro con confirmación
- ✏️ Actualizar libro (en progreso)

### Búsquedas ✅
- Buscar por título
- Buscar por ID
- Ver todos los libros

### Validaciones ✅
- ISBN de exactamente 13 dígitos numéricos
- Detección de ISBN duplicado antes de insertar
- Validación de formato (solo valores del enum: paperback, hardcover, ebook, audiobook)
- Confirmación antes de eliminar un libro
- Autoras, editoriales y géneros: si no existen, se crean automáticamente

---

## Gestión de credenciales

Las credenciales de la base de datos se gestionan mediante un archivo `.env` que **nunca debe subirse al repositorio**.

El proyecto incluye un archivo `.env.example` como plantilla:

```env
DB_USER=your_user
DB_PASS=your_password
```

Cada persona del equipo debe crear su propio archivo `.env` con sus datos locales:

```bash
cp .env.example .env
```

El archivo `.env` está incluido en `.gitignore` para evitar su publicación accidental.

---

## Requisitos previos

- Java 25
- Maven 3.x
- PostgreSQL 16+
- Git

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone <https://github.com/FeministLibrary-FactoriaF5/FeministLibrary.git>
cd FeministLibrary
```

### 2. Crear la base de datos y las tablas

```bash
psql -U postgres -c "CREATE DATABASE feminist_library;"
psql -U postgres -d feminist_library -f schema.sql
```

### 3. Configurar el archivo `.env`

```bash
cp .env.example .env
```

Editar `.env` con las credenciales locales de PostgreSQL.

### 4. Compilar el proyecto

```bash
mvn clean install
```

### 5. Ejecutar la aplicación

```bash
mvn exec:java
```

---

## Tests

El proyecto incluye tests unitarios con **JUnit 5** y **Mockito**.

Para ejecutar los tests:

```bash
mvn test
```

---

## Flujo de trabajo con Git

```text
main
 └── dev
      ├── feat/create-book
      ├── feat/read-all-books
      ├── feat/update-book
      ├── feat/delete-book
      ├── feat/search-by-title
      ├── feat/create-author
      └── feat/create-publisher
```

El flujo habitual es:
1. Crear una rama desde `dev`
2. Desarrollar la funcionalidad
3. Hacer commit con mensajes en formato Conventional Commits
4. Subir la rama y crear un Pull Request hacia `dev`
5. Revisar y hacer merge cuando el código funcione correctamente

---

## Buenas prácticas aplicadas

- Patrón MVC para separar responsabilidades
- Patrón Repository para desacoplar el acceso a datos
- Interfaces para cada repositorio (facilita tests con mocks)
- Mensajes de usuario centralizados en la View (no en el Controller ni Repository)
- Protección de credenciales con `.env` y `.gitignore`
- Base de datos normalizada en 3FN
- Validaciones tanto en la View (formato) como en el Controller (lógica de negocio)
- Colores ANSI en terminal para mejorar la experiencia de uso
- Control de versiones con ramas y Pull Requests

---

## Equipo de desarrollo

Proyecto desarrollado como práctica formativa de backend con Java y PostgreSQL.

| Integrante | GitHub          |
|---|-----------------|
| Fabiana | @fabileoruf               |
| Aïda | @AidaG91        |
| Rose | @rosana50factoria |
| Damaris | @damcb1         |

---

## Licencia

Este proyecto ha sido desarrollado con fines educativos.