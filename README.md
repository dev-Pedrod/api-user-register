# Api-user-register
Back-end Developed in Java 17 and Spring Boot.

## Technologies

Here are the technologies used in this project:

- Java 17
- Spring Boot
- H2 Database
- PostgreSQL
- AWS SQS
- Junit 5
- Mockito

## Data Base
you can test locally by configuring application.properties like this:

```
# database
# spring.datasource.url=${DATABASE_URL}

# database H2
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# jpa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true

# AWS
amazon.accessKey=${accessKey : nokey}
amazon.secretKey=${secretKey : nokey}
```

- Access the H2 database at: [H2 Database](http://localhost:8080/h2-console/)
  
 *(Remember to run the project before accessing)*
 
---
By dev-Pedrod  [*See my Linkedin*](https://www.linkedin.com/in/pedrooliveiradev/)

