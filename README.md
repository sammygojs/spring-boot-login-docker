Java spring-boot-login-docker

1. install
spring web
spring data mongodb
security - encryption
devtools - restart
Lombok - boiler code 

packages
controller
model
repository
config
service

2. add mongo URI to application properties
spring.data.mongodb.uri=mongodb://localhost:27017/login_db

3. Add User.java in model pkg

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String password;
}

4. Adding UserRepository.java in repository pkg to check for Email

import com.example.login.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
User findByEmail(String email);
}

5. Adding UserService.java in service pkg

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}

Issues: bCryptPasswordEncoder @Bean was not found. so added following in main file

@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
return new BCryptPasswordEncoder();
}

Solved it.

6. Adding AuthController in Controller pkg

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }
}

7. Adding SecurityConfig in config pkg

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
return new BCryptPasswordEncoder();
}
}

So, removed the Bean code from main file as this was written.

8. Downloading Docker Desktop abd adding docker-compose.yml at root

version: '3.8'

services:
mongo:
image: mongo:latest
container_name: mongo
ports:
- 27017:27017
volumes:
- mongo-data:/data/db

app:
build: .
container_name: spring-boot-app
ports:
- 8080:8080
depends_on:
- mongo
environment:
SPRING_DATA_MONGODB_URI: mongodb://mongo:27017/login_db

volumes:
mongo-data:

9. as the project is in gradle, we run the command to create a .jar for spring app

gradlew clean build

10. Create Dockerfile and add instructions

[//]: # (# Use a base image with OpenJDK 17)
FROM openjdk:17-jdk-slim

[//]: # (# Set the working directory)
WORKDIR /app

[//]: # (# Copy the built jar file from the build/libs directory to the container)
COPY build/libs/spring-boot-login-docker-0.0.1-SNAPSHOT.jar app.jar

[//]: # (# Expose the port the application will run on)
EXPOSE 8080

[//]: # (# Command to run the application)
ENTRYPOINT ["java", "-jar", "app.jar"]

10. Run Docker command 

docker-compose up --build

11. Returning 200 OK if credentials are same. Add following in UserService and AuthController

Check commit

note: _class is automatically created in mongo, it is for dequery purposes.

12.
implementation 'io.jsonwebtoken:jjwt:0.9.1' in build.gradle

gradlew build --refresh-dependencies


13. Adding protected endpoint

14. 


