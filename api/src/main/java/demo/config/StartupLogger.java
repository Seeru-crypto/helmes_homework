package demo.config;

import demo.model.Sector;
import demo.service.SectorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StartupLogger {
    private final Environment env;

    @Autowired
    private SectorService sectorService;

    @PostConstruct
    public void initApplication() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        if (profiles.contains("local")) {
            log.info("App hosted at " + "http://localhost:" + env.getProperty("server.port"));
            log.info("local profile active");
            log.info("Swagger enabled at http://localhost:8880/api/swagger-ui/index.html#/");

            Sector parent = sectorService.save(null, "parent");
            sectorService.save(parent.getId(), "child sector");

        }
        else if (profiles.contains("production")){
            log.info("production profile active");
        }
    }
}
