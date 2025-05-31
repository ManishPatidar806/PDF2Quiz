# PDF2Quiz - Backend

## Project Overview
PDF2Quiz is an AI-powered platform where users can upload a PDF document, and the system automatically generates quizzes to test their knowledge.
It also features a smart AI chatbot that answers user queries based on the uploaded content!
The backend is built using **Spring Framework** and follows best practices for scalability, maintainability, and performance.

## Features
- 📄 Upload PDFs – Upload any study material or notes.
- 🧠 Auto-Generated Quizzes – Get Mcq Question instantly.
- 🤖 AI Chatbot Support – Ask doubts, get explanations, and learn interactively.
- 📝 Take Quizzes – Practice generated questions.


## Technologies Used
- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL 
- **Build Tool**: Maven
- **Version Control**: Git

## Prerequisites
- Java 21 or higher
- Maven 3.6+
- MySQL or any compatible database
- IDE (e.g., IntelliJ IDEA, Eclipse)

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/ManishPatidar806/PDF2Quiz.git
    ```
2. Navigate to the project directory:
    ```bash
    cd PDF2Quiz
    ```
3. Configure this in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password

    spring.ai.ollama.base-url=your_Illama_url
    spring.ai.ollama.chat.options.model=Ollama_model
    spring.ai.ollama.chat.options.temperature=0.7

    spring.servlet.multipart.max-file-size=50MB
    spring.servlet.multipart.max-request-size=50MB

    ```

4. Build the project:
    ```bash
    mvn clean install
    ```
5. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## API Endpoints
-For API Endpoins Use Swagger Url :http://localhost:8080/swagger-ui.html



## License
This project is licensed under the [MIT License](LICENSE).
