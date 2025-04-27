package lorgar.avrelian.task0021;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "LeetCode task № 21",
                description = "An application for solving task № 21 of the LeetCode website and verifying its results in Java",
                version = "0.1.0",
                contact = @Contact(
                        name = "Victor Tokovenko",
                        email = "victor-14-244@mail.ru",
                        url = "https://github.com/Lorgar-Avrelian"
                )
        )
)
public class Task0021Application {

    public static void main(String[] args) {
        SpringApplication.run(Task0021Application.class, args);
    }

}
