package demo;

import demo.controller.dto.SectorDto;
import demo.model.Sector;
import demo.model.User;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class ContextIntegrationTest extends BaseIntegrationTest {

    private static final List<Object> createdEntities = new ArrayList<>();

    protected Sector createSector(String name, Long parentId, int value) {
        Sector sector = sectorService.save(parentId, name, value);
        entityManager.persist(sector);
        return sector;
    }

    protected User createUser(String name, boolean agreeToTerms, List<Sector> sectors) {
        User user = userService.save(new User().setName(name).setAgreeToTerms(agreeToTerms).setSectors(sectors));
        entityManager.persist(user);
        return user;
    }

    public static void clear() {
        createdEntities.clear();
    }

    public static void persistCreatedEntities(EntityManager em) {
        createdEntities.forEach(em::persist);
        em.flush();
    }
}
