# Chat API

A **Spring Boot** application that provides a REST API for handling chat-based interactions.

## Features

- RESTful API with Spring Boot
- JSON-based communication
- Easily extendable architecture
- Supports **Spring Boot 3+**, **Java 17+**
- Maven/Gradle build system
---
## Prerequisites

Ensure you have the following installed:

- **Java 21+** ([Download](https://adoptium.net/))
- **Maven 3+** ([Download](https://maven.apache.org/download.cgi))
- **Postman** (Optional, for API testing)
---
## 🛠️ Setup & Installation

Clone the repository:

```sh
git clone https://github.com/w0rm29/chatbot-app.git
cd chatbot-app
```
---
## Run

```shell
./mvnw spring-boot:run
```
---

## API Endpoints

```jsonpath
POST /chat/ask
Content-Type: application/json
Request-Body
{
  "message": "Hello, chatbot!"
}
```
---

## Project Structure

### Explanation of Key Directories and Files:

- **`controller/`** → Handles REST API requests.
- **`service/`** → Contains business logic.
- **`model/`** → Defines request/response data models.
- **`resources/`** → Stores application configuration.
    - `application.yml` → Preferred configuration file using YAML format.
    - `application.properties` → Alternative properties-based configuration.
- **`test/`** → Contains unit and integration tests.
- **`pom.xml` / `build.gradle`** → Manages dependencies.
- **`README.md`** → Documentation.

This structure follows best practices for a **Spring Boot** application, making it modular and easy to maintain.

---

## 🛠️ Made with ❤️

Made with ❤️ using:

-  [Spring Boot](https://spring.io/projects/spring-boot) – Backend framework
-  [LangChain](https://www.langchain.com/) – AI/LLM Orchestration
-  [Ollama](https://ollama.com/) – Local LLM Inference


