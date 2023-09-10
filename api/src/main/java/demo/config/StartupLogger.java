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

        if (sectorService.findAll().isEmpty()){
            Sector manufacturing = sectorService.save(null, "Manufacturing");
            sectorService.save(manufacturing.getId(), "Construction materials");
            sectorService.save(manufacturing.getId(), "Electronics and optics");

            Sector bakery = sectorService.save(manufacturing.getId(), "Food and Beverage");
            sectorService.save(bakery.getId(), "bakery & confectionery products");
            sectorService.save(bakery.getId(), "Beverages");
            sectorService.save(bakery.getId(), "Fish & fish products");

            Sector furniture = sectorService.save(manufacturing.getId(), "Furniture");
            sectorService.save(furniture.getId(), "Bathroom/sauna");
            sectorService.save(furniture.getId(), "Bedroom");
            sectorService.save(furniture.getId(), "Children's room");

            Sector service = sectorService.save(null, "Service");
            sectorService.save(service.getId(), "Business services");
            sectorService.save(service.getId(), "Engineering");
            sectorService.save(service.getId(), "Telecommunications");
        }

        if (profiles.contains("local")) {
            log.info("App hosted at " + "http://localhost:" + env.getProperty("server.port"));
            log.info("local profile active");
            log.info("Swagger enabled at http://localhost:8880/api/swagger-ui/index.html#/");
        }
        else if (profiles.contains("production")){
            log.info("production profile active");
        }
    }
}
