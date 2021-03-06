package hotel;


//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import org.springframework.beans.factory.annotation.Value;
import hotel.storage.StorageProperties;
import hotel.storage.StorageService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
//@SecurityScheme(name = "step", scheme = "jwt", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello friends!");
        SpringApplication.run(Main.class, args);
    }
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            try {
                storageService.init();
            }
            catch (Exception ex) {
                System.out.println("-----Хюстон у нас проблеми-----"+ ex.getMessage());
            }
        };
    }

}
