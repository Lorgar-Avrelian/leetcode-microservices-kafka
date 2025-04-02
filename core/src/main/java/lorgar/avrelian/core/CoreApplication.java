package lorgar.avrelian.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "LeetCode tasks",
                description = "An application for solving tasks and verifying them from the LeetCode website in Java",
                version = "0.1.0",
                contact = @Contact(
                        name = "Victor Tokovenko",
                        email = "victor-14-244@mail.ru",
                        url = "https://github.com/Lorgar-Avrelian"
                )
        )
)
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
