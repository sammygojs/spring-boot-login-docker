Java spring-boot-login-docker

install
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

add mongo URI to application properties
spring.data.mongodb.uri=mongodb://localhost:27017/login_db

Add User.java in model

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

Adding UserRepository.java in repository to check for Email

import com.example.login.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
User findByEmail(String email);
}
