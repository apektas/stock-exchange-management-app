# Stock Exchange Management Application

This application is a Java-based simple stock exchange management application. The application provides functionalities to manage stock exchanges and stocks via REST endpoints.

## Table of Contents

1. [Technologies](#technologies)
2. [Requirements](#pre-requirements)
3. [Features](#features)
4. [Building and Running the Application](#building-and-running-the-application)
5. [REST Endpoints](#rest-endpoints)
6. [Security](#security)
7. [Future Work](#future-work)


## Technologies
* Java 19
* Maven
* H2 Database

## Pre requirements
* OpenJDK 17
* Maven


## Features

- List all stocks inside a stock exchange
- Create a new stock in the system
- Add given stock to the stock exchange
- Delete provided stock from a stock exchange
- Update price of a given stock
- Delete given stock from the system


## Building and Running the Application

### Build the Application and Create Jar file

```sh
mvn clean compile && mvn package
```

### Run the Application

```sh
java -jar ./target/sems-0.0.1-SNAPSHOT.jar
```

The application will start listening every interface (0.0.0.0) on default port 8080. You can change the default by providing env variable. `http://localhost:8080`.

## REST Endpoints
You can download Postman collection and explore the REST Endpoint. First get Bearer Token from the `/api/v1/auth/token` token and get Bearer token. Then utilize the access_token for the subsequent requests.

[Postman Collection](docs/postman/Stock-Management-App.postman_collection.json)

## Security

The application uses basic JWT based authentication mechanism. The default user credentials are as follows (you can update these settings inside application.properties):

- **Username:** `admin`
- **Password:** `admin`

P.S: Please use strong stock.app.jwtSecret value to secure JWT tokens. You can overwrite value inside application.properties file.


## Database Initialization

As app is developed MVP, it uses H2 in-memory database. The database schema and initial data files are located inside `src/main/resources`. The schema is automatically created and data.sql is populated inside the database.

You can connect H2 web interface by following credentials.

- **Username:** `admin`
- **Password:** `admin`

## Future Work

- Add database locking methods i.e. optimistic or Pessimistic locking.
- Implement role-based access control mechanism
- Pagination support for some endpoint - like list stocks of a stock exchange.
- Native query can be used some specific cases to optimize heavy lifting Hibernate operations.
- Adding immutable log store can be beneficial.
- Implement more robust error handling and validation
- Improve the API documentation using Swagger
- Adding more unit and integration tests to cover edge cases.
- Dockerize the app
- Add load balancer like nginx and use TLS certificate to provide secure communication in transit.