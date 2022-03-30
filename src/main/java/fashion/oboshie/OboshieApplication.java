package fashion.oboshie;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Oboshie Fashion",version = "1.0", description = "Oboshie Fashion Backend APIs"))
public class OboshieApplication {

	public static Logger LOGGER = LoggerFactory.getLogger(OboshieApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Swagger API: localhost:8080/swagger-ui.html");
		SpringApplication.run(OboshieApplication.class, args);
	}

}
