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

    protected void createFullSectorTree() {
        Sector sectorA = createSector("A", null, 1);
        Sector sectorB = createSector("B", sectorA.getId(), 2);
        Sector sectorC = createSector("C", sectorA.getId(), 3);
        Sector sectorD = createSector("D", sectorB.getId(), 4);
        Sector sectorE = createSector("E", sectorB.getId(), 5);
        Sector sectorF = createSector("F", sectorC.getId(), 6);
        Sector sectorG = createSector("G", sectorC.getId(), 7);
        Sector sectorH = createSector("H", sectorD.getId(), 8);
        Sector sectorI = createSector("I", sectorD.getId(), 9);
        Sector sectorJ = createSector("J", sectorE.getId(), 10);
        Sector sectorK = createSector("K", sectorE.getId(), 11);
        Sector sectorL = createSector("L", sectorF.getId(), 12);
        Sector sectorM = createSector("M", sectorF.getId(), 13);
        Sector sectorN = createSector("N", sectorG.getId(), 14);
        Sector sectorO = createSector("O", sectorG.getId(), 15);
        Sector sectorP = createSector("P", sectorH.getId(), 16);
        Sector sectorQ = createSector("Q", sectorH.getId(), 17);
        Sector sectorR = createSector("R", sectorI.getId(), 18);
        Sector sectorS = createSector("S", sectorI.getId(), 19);
        Sector sectorT = createSector("T", sectorJ.getId(), 20);
        Sector sectorU = createSector("U", sectorJ.getId(), 21);
        Sector sectorV = createSector("V", sectorK.getId(), 22);
        Sector sectorW = createSector("W", sectorK.getId(), 23);
        Sector sectorX = createSector("X", sectorL.getId(), 24);
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
