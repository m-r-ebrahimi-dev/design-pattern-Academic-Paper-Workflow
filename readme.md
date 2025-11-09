# Academic Paper Workflow: State Design Pattern Implementation

[![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Design Pattern](https://img.shields.io/badge/Design%20Pattern-State-blue.svg)](https://refactoring.guru/design-patterns/state)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

This project is a practical implementation of the **State Design Pattern** in a Spring Boot application. It models the
lifecycle of an academic paper, demonstrating how to manage complex, state-dependent behavior in a clean, maintainable,
and scalable way by replacing large conditional blocks with polymorphic state objects.

## Key Features & Architecture

- **Stateless State Objects:** State classes are thread-safe, reusable Singletons managed by Spring.
- **Decoupled Domain Model:** The `Paper` entity is decoupled from framework logic using an `@EntityListener` to manage
  state transitions.
- **Rich Domain Model:** The `Paper` entity is not just a data holder; it contains the business logic for its own
  lifecycle, delegating actions to its current state object.
- **Centralized Exception Handling:** A `@ControllerAdvice` provides consistent, meaningful error responses for invalid
  actions or authorization failures.
- **Dockerized Environment:** Includes a `docker-compose.yml` for a one-command Oracle XE database setup.

## State Transition Diagram

```mermaid
graph TD
    A[Draft] -->|submit()| B(Submitted);
    B -->|assignToReviewer()| C(Under Review);
    B -->|reject()| G(Rejected);
    C -->|requestRevision()| D(Needs Revision);
    C -->|accept()| E(Accepted);
    C -->|reject()| G;
    D -->|submit()| C;
    E -->|publish()| F(Published);

    subgraph Final States
        G; F;
    end
```

## Technology Stack

- **Java 21+** & **Maven**
- **Spring Boot 3.x** (Web, Data JPA)
- **Oracle Database** (via Docker)
- **Docker & Docker Compose**

## How to Run

### Prerequisites

- Java 21+
- Maven 3.6+
- Docker and Docker Compose

### Step 1: Start the Database

From the project root, start the Oracle database container. It will be pre-configured with the necessary user and
schema.
*(Note: It may take a minute for the database to fully initialize on first launch.)*

```bash
docker-compose up -d
```

### Step 2: Run the Application

Once the database is running, start the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Workflow Demonstration (API Usage)

The following steps demonstrate a complete workflow using `cURL`. *(Tip: Pipe the output to `jq` for pretty-printed
JSON).*

### 1. Setup: Create Users

First, let's create our actors: an author, an editor, and a reviewer. Note their IDs from the responses.
*(We'll assume they get IDs 1, 2, and 3 respectively)*.

```bash
# Create an AUTHOR
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d \
'{"username": "author_user", "fullName": "Dr. Alice", "role": "AUTHOR"}' | jq

# Create an EDITOR
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d \
'{"username": "editor_user", "fullName": "Prof. Bob", "role": "EDITOR"}' | jq

# Create a REVIEWER
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d \
'{"username": "reviewer_user", "fullName": "Dr. Carol", "role": "REVIEWER"}' | jq
```

### 2. Author Creates and Submits a Paper

The author (ID `1`) creates a new paper. Its initial state is `DRAFT`.

```bash
# Create a paper (we'll get back an ID, let's assume it's 1)
curl -X POST http://localhost:8080/api/papers -H "Content-Type: application/json" -d \
'{"title": "The State of Modern Software", "content": "...", "authorId": 1}' | jq
```

Response will show `status: "DRAFT"`. Let's assume the paper ID is `1`.

Now, the author submits the paper for review.

```bash
# Author (userId: 1) submits paper (id: 1)
curl -X POST http://localhost:8080/api/papers/1/submit -H "Content-Type: application/json" -d \
'{"userId": 1}'
```

Checking the paper's status now will show `SUBMITTED`.
`curl http://localhost:8080/api/papers/1 | jq`

### 3. Editor's Turn: Assign for Review

The editor (ID `2`) assigns the paper to a reviewer (ID `3`).

```bash
# Editor (editorId: 2) assigns paper (id: 1) to Reviewer (reviewerId: 3)
curl -X POST http://localhost:8080/api/papers/1/assign -H "Content-Type: application/json" -d \
'{"editorId": 2, "reviewerId": 3}'
```

The paper's state is now `UNDER_REVIEW`.

### 4. Editor's Decision

After the review, the editor makes a decision.

#### Path A: Happy Path (Accept & Publish)

```bash
# 1. Editor (userId: 2) accepts the paper
curl -X POST http://localhost:8080/api/papers/1/accept -H "Content-Type: application/json" -d \
'{"userId": 2}'

# 2. Check status (it's now "ACCEPTED")
# 3. Editor (userId: 2) publishes the paper
curl -X POST http://localhost:8080/api/papers/1/publish -H "Content-Type: application/json" -d \
'{"userId": 2}'
```

The paper is now in its final `PUBLISHED` state.

#### Path B: Revision Loop

What if a revision is needed?

```bash
# 1. Editor (userId: 2) requests a revision
curl -X POST http://localhost:8080/api/papers/1/revise -H "Content-Type: application/json" -d \
'{"userId": 2}'

# 2. Status is now "NEEDS_REVISION". The author (userId: 1) must re-submit.
curl -X POST http://localhost:8080/api/papers/1/submit -H "Content-Type: application/json" -d \
'{"userId": 1}'
```

The paper is now back in the `UNDER_REVIEW` state, and the cycle can continue.

### 5. Example of an Invalid Action

What happens if the author tries to accept their own paper? The State Pattern and our exception handler will prevent
this.

```bash
# Author (userId: 1) tries to accept paper (id: 1) while it's UNDER_REVIEW
curl -i -X POST http://localhost:8080/api/papers/1/accept -H "Content-Type: application/json" -d \
'{"userId": 1}'
```

The server will respond with a `403 Forbidden` status and a clear error message:

```json
{
  "error": "Only editors can accept papers."
}
```

This demonstrates the power of encapsulating state-specific rules within dedicated objects.

### Shutting Down

To stop and remove the database container, run:

```bash
docker-compose down
```

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.