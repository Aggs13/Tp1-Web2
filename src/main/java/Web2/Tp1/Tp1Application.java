package Web2.Tp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tp1Application {

	public static void main(String[] args) {
		SpringApplication.run(Tp1Application.class, args);
		System.out.println("SWAGGER: http://localhost:8081/swagger-ui.html");
	}

}
