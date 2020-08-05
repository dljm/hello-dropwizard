# Hello Dropwizard!

## Usage

- Setup your MySQL instance via docker or using a local instance
- Enable `JDBC_FLYWAY` in `config.yml` to run flyway migration to initialize the schema
- IntelliJ program arguments `server config.yml`
- Create Maven project from `pom.xml`

## Endpoints
Defaults:
- Get all existing saying records
    - GET http://localhost:8080/hello-world/
- Get a saying record by id
    - GET http://localhost:8080/hello-world/:id
- Create a new saying record
    - POST http://localhost:8080/hello-world/
    ```
      {
        "content" : "Hello, %s!", 
        "active": "true"
       }
- Update an existing saying by id
    - PUT http://localhost:8080/hello-world/:id
- Delete an existing saying by id
  - DELETE http://localhost:8080/hello-world/:id
- Get a random existing active saying
  - GET http://localhost:8080/hello-world/hello?name=Archer