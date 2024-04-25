package demo.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StartupLogger {
    private final Environment env;

    @PostConstruct
    public void initApplication() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        if (profiles.contains("local")) {
            log.info("local profile active");
            log.info("App hosted at " + "http://localhost:" + env.getProperty("server.port"));
            log.info("Swagger enabled at http://localhost:{}/api/swagger-ui/index.html#/", env.getProperty("server.port"));
            log.info("Health endpoint available at http://localhost:{}/api/actuator/health", env.getProperty("server.port"));
        }
        else if (profiles.contains("production")){
            log.info("production profile active");
        }
    }
}
