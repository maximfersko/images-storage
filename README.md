## Images storage

This project is a web application designed for uploading and storing photos.

### Build and Run:

#### Using Docker

```zsh
    docker-compose up
```

### Description and Features

The application supports two user roles: administrator and user. Users can upload and delete their own images, while
administrators have access to and can delete images from all users. During registration, a user can specify their role
by checking the "Register as Admin" checkbox.

#### Registration Page

![](/images/sign-up.png)

After logging in, the user dashboard provides two tabs:

#### Upload Images

![](/images/upload.png)

#### View Images

![](/images/view.png)

## Database Schema

![](/images/db_scheme.png)

### Project Dependencies

#### The project uses Maven for dependency management. Below are the dependencies:

* Spring Boot Starter Data JPA: org.springframework.boot:spring-boot-starter-data-jpa
* Commons Lang 3: org.apache.commons:commons-lang3:${commons.version}
* SLF4J API: org.slf4j:slf4j-api:${log.version}
* JJWT API: io.jsonwebtoken:jjwt-api:${jwt.version}
* JJWT Implementation: io.jsonwebtoken:jjwt-impl:${jwt.version}
* JJWT Jackson: io.jsonwebtoken:jjwt-jackson:${jwt.version}
* Spring Boot Starter Web: org.springframework.boot:spring-boot-starter-web
* Spring Boot DevTools: org.springframework.boot:spring-boot-devtools
* PostgreSQL Driver: org.postgresql:postgresql
* Lombok: org.projectlombok:lombok

### Additional Properties

* Java Version: 17
* MapStruct Version: 1.5.5.Final
* JWT Version: 0.11.5
* Hibernate Validator Version: 8.0.1.Final
* Commons Version: 3.14.0
* Log Version: 2.0.13
