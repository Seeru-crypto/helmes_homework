# Helmes homework

## setup via docker
- navigate to /api
- Build gradle project with either gradle build or gradle assemble
- navigate to project root
- run ``docker-compose up -d``
- application should be available at http://localhost:8880/
    - swagger should be available at http://localhost:8880/api/swagger-ui/index.html

NB: When running UI in docker, during the first render an error might occur. I could not figure out why this happens, but it does not affect the functionality of the application.
If it occurs just reload the browser. 

## Stack
- back-end: I used spring boot java with swagger for API documentation and testcontainers for in-depth integration testing.
- database: a simple postgre database which is initialized with flyway, the schema is generated with custom SQL script and validated by hibernate
- front-end: decided to use next.js with axios for HTTP requests and zustand for a lightweight state managment and ant UI library
- reverse-proxy: A simple ngnix reverse-proxy, which proxys all /api requests to the back-end, while forwarding all next.js API calls to back-end as well

## task
1. Correct all of the deficiencies in index.html (located in root folder)

2. "Sectors" selectbox:
   2.1. Add all the entries from the "Sectors" selectbox to database
   2.2. Compose the "Sectors" selectbox using data from database

3. Perform the following activities after the "Save" button has been pressed:
   3.1. Validate all input data (all fields are mandatory)
   3.2. Store all input data to the database (Name, Sectors, Agree to terms)
   3.3. Refill the form using stored data
   3.4. Allow the user to edit his/her own data during the session