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

