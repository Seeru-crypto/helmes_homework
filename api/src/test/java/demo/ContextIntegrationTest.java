package demo;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class ContextIntegrationTest extends BaseIntegrationTest {

    private static final List<Object> createdEntities = new ArrayList<>();


    public static void clear() {
        createdEntities.clear();
    }

    public static void persistCreatedEntities( EntityManager em ) {
        createdEntities.forEach(em::persist);
        em.flush();
    }
}
