# BlogNest - Personal Blogging Platform

A server-rendered personal blogging web application built with Java and Spring Boot.

The application provides a public blog where visitors can read articles and an authenticated admin area where the blog owner can create, edit, and delete articles.

---

## Features

### Public

- View all published articles
- View individual articles
- View article publication dates
- No authentication required for public content
- Custom 404 page for articles that do not exist

### Admin

- Secure admin dashboard
- Create new articles
- Edit existing articles
- Delete articles
- Automatic publication dates
- Server-side validation
- Validation error messages
- Delete confirmation dialog
- Admin-aware article navigation

### Security

- Spring Security
- HTTP Basic authentication
- Protected admin routes
- Public article routes
- CSRF protection
- Authentication required for article management

---

## Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate

### Frontend

- Thymeleaf
- HTML
- CSS
- JavaScript

### Database

- PostgreSQL 16
- Docker

### Build & Version Control

- Maven
- Git
- GitHub

---

## Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service Interface
    ↓
Service Implementation
    ↓
Repository
    ↓
PostgreSQL
```

### Controller Layer

Handles HTTP requests and prepares data for the Thymeleaf views.

```text
HomeController
ArticleController
AdminController
```

### Service Layer

Contains the application's business logic.

The service layer is separated into an interface and implementation:

```text
ArticleService
      ↓
ArticleServiceImpl
```

### Repository Layer

Uses Spring Data JPA to communicate with PostgreSQL.

```text
ArticleRepository
```

### Entity

The main domain entity is:

```text
Article
├── id
├── title
├── content
└── publicationDate
```

### DTOs

The application uses DTOs to separate request and response data from the JPA entity.

```text
ArticleRequest
ArticleResponse
```

---

## Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── nimesh/
    │           └── personal_blog/
    │               ├── config/
    │               │   └── SecurityConfig.java
    │               │
    │               ├── controller/
    │               │   ├── HomeController.java
    │               │   ├── ArticleController.java
    │               │   └── AdminController.java
    │               │
    │               ├── dto/
    │               │   ├── ArticleRequest.java
    │               │   └── ArticleResponse.java
    │               │
    │               ├── entity/
    │               │   └── Article.java
    │               │
    │               ├── exception/
    │               │   ├── ArticleNotFoundException.java
    │               │   └── GlobalExceptionHandler.java
    │               │
    │               ├── repository/
    │               │   └── ArticleRepository.java
    │               │
    │               ├── service/
    │               │   ├── ArticleService.java
    │               │   └── ArticleServiceImpl.java
    │               │
    │               └── PersonalBlogApplication.java
    │
    └── resources/
        ├── templates/
        │   ├── home.html
        │   ├── article.html
        │   ├── admin/
        │   │   ├── dashboard.html
        │   │   ├── new.html
        │   │   └── edit.html
        │   └── error/
        │       └── 404.html
        │
        └── static/
            └── css/
                └── style.css
```

---

## Routes

### Public Routes

| Method | URL | Description |
|---|---|---|
| GET | `/` | View all articles |
| GET | `/articles/{id}` | View an individual article |

### Admin Routes

| Method | URL | Description |
|---|---|---|
| GET | `/admin` | Admin dashboard |
| GET | `/admin/new` | Show create article form |
| POST | `/admin/new` | Create article |
| GET | `/admin/edit/{id}` | Show edit article form |
| POST | `/admin/edit/{id}` | Update article |
| POST | `/admin/delete/{id}` | Delete article |

---

## Article Management

### Create

The admin provides:

- Article title
- Article content

The publication date is automatically assigned when the article is created.

```java
LocalDate.now()
```

The publication date is not changed when an article is edited.

### Read

Visitors can view:

- A list of articles
- Individual article content
- Publication dates

### Update

The admin can update:

- Title
- Content

The original publication date is preserved.

### Delete

Articles can be deleted from the admin dashboard.

Deletion requires confirmation through a custom confirmation dialog before the request is submitted.

The delete operation uses:

```text
POST /admin/delete/{id}
```

---

## Validation

Article creation and editing use server-side validation with Jakarta Bean Validation.

The following fields are required:

- Title
- Content

Example:

```java
@NotBlank(message = "Title is required")
private String title;
```

Validation errors are displayed directly on the corresponding form.

---

## Exception Handling

The application uses a custom exception:

```text
ArticleNotFoundException
```

when an article cannot be found.

A global exception handler handles this exception and returns the custom 404 page.

For example:

```text
/articles/999
```

will display the 404 page if article `999` does not exist.

---

## Security

Spring Security protects the administrative area while keeping the public blog accessible.

The access model is:

```text
/                  → Public
/articles/**       → Public

/admin/**          → Authentication required
```

The application uses HTTP Basic authentication for the admin area.

CSRF protection remains enabled for state-changing requests, including:

```text
POST /admin/new
POST /admin/edit/{id}
POST /admin/delete/{id}
```

---

## Database

The application uses PostgreSQL 16 for persistent article storage.

PostgreSQL runs locally using Docker.

Example:

```bash
docker run --name personal-blog-db \
  -e POSTGRES_DB=personal_blog \
  -e POSTGRES_USER=nimesh \
  -e POSTGRES_PASSWORD=your_password \
  -p 5434:5432 \
  -d postgres:16
```

The application communicates with PostgreSQL through Spring Data JPA and Hibernate.

---

## Configuration

Database configuration is stored locally in:

```text
src/main/resources/application.properties
```

The file is excluded from Git because it contains database credentials.

Example:

```properties
spring.application.name=personal-blog

spring.datasource.url=jdbc:postgresql://localhost:5434/personal_blog
spring.datasource.username=nimesh
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Replace the credentials with your local PostgreSQL configuration.

---

## Running the Application

### 1. Start PostgreSQL

Make sure the PostgreSQL container is running:

```bash
docker ps
```

If it is stopped:

```bash
docker start personal-blog-db
```

### 2. Configure the Database

Create:

```text
src/main/resources/application.properties
```

and provide your local PostgreSQL credentials.

### 3. Run the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

The application can also be started through an IDE by running:

```text
PersonalBlogApplication.java
```

### 4. Open the Application

Public blog:

```text
http://localhost:8080/
```

Admin dashboard:

```text
http://localhost:8080/admin
```

The admin area requires authentication.

---

## UI

The application uses a simple hand-drawn visual style throughout the public and administrative interfaces.

The design includes:

- Bold typography
- Rounded borders
- Minimal layouts
- Light background
- Simple black outlines
- Consistent spacing
- Custom confirmation dialogs
- Clear validation feedback

The admin interface is designed to remain visually consistent with the public blog.

---

## Testing

The application is tested across the main user flows, including:

- Creating an article
- Viewing articles
- Editing an article
- Deleting an article
- Validation errors
- Article-not-found handling
- Public access
- Admin authentication
- Protected admin routes
- CSRF-protected form submissions

---

## Git & GitHub

The project is version-controlled using Git and hosted on GitHub.

Example workflow:

```bash
git add .
git commit -m "your commit message"
git push
```

Local configuration files containing credentials are excluded from version control using `.gitignore`.

---

## Future Improvements

Possible future improvements include:

- Article search
- Pagination
- Markdown or rich-text editing
- Draft and published article states
- Image uploads
- Article categories and tags
- Improved responsive design
- Cloud deployment
- Additional automated tests

---

## License

This project is a personal learning and portfolio project.
