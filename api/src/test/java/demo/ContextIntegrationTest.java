package demo;

import demo.model.Sector;
import demo.model.User;
import jakarta.persistence.EntityManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContextIntegrationTest extends BaseIntegrationTest {

  private static final List<Object> createdEntities = new ArrayList<>();

  protected Sector createSector(String name, Long parentId, int value) {
    return sectorService.save(parentId, name, value);
  }

  protected Instant getInstantFromString(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
    return localDateTime.toInstant(ZoneOffset.UTC);
  }

  protected List<User> createDefaultUsers() {
    if (!sectorExists(1L)) {
      createFullSectorTree();
    }
    List<User> users = new ArrayList<>();
    users.add(createUser("qJohn Does", true, List.of(findSectorById(1L)), "johndoe1234@gmail.com", "+123 123456789",getInstantFromString("1985-07-15 12:30:00")));
    users.add(createUser("qJane Does", true, List.of(findSectorById(2L), findSectorById(11L)), "jane_smith123@example.com", "+372 1234567",getInstantFromString("1990-04-28 08:15:00")));
    users.add(createUser("qJack Doesn't", true, List.of(findSectorById(8L), findSectorById(11L), findSectorById(13L)), "bob.smith@company.co.uk", "+44 1234567890",getInstantFromString("1995-10-10 17:45:00")));
    users.add(createUser("qJames Memorial", true, List.of(findSectorById(1L), findSectorById(20L)), "mary.smith@email.com", "+1 1234567890",getInstantFromString("1998-12-01 20:00:00")));
    return users;
  }

  protected void createFullSectorTree() {
    Sector sectorA = createSector("Manufacturing", null, 1);
    Sector sectorB = createSector("Construction materials", sectorA.getId(), 11);
    Sector sectorC = createSector("Electronics and Optics", sectorA.getId(), 12);
    Sector sectorD = createSector("Food and Beverage", sectorA.getId(), 13);
    Sector sectorE = createSector("Bakery & confectionery products", sectorD.getId(), 131);
    Sector sectorF = createSector("Beverages", sectorD.getId(), 132);
    Sector sectorG = createSector("Fish & fish products", sectorD.getId(), 133);
    Sector sectorH = createSector("Milk & dairy products", sectorD.getId(), 134);
    Sector sectorI = createSector("Other", sectorD.getId(), 135);
    Sector sectorJ = createSector("Sweets & snack food", sectorD.getId(), 136);
    Sector sectorK = createSector("Furniture", sectorA.getId(), 14);
    Sector sectorL = createSector("Bathroom/sauna", sectorK.getId(), 141);
    Sector sectorM = createSector("Bedroom", sectorK.getId(), 142);
    Sector sectorN = createSector("Children’s room", sectorK.getId(), 143);
    Sector sectorO = createSector("Kitchen", sectorK.getId(), 144);
    Sector sectorP = createSector("Living room", sectorK.getId(), 145);
    Sector sectorQ = createSector("Office", sectorK.getId(), 146);
    Sector sectorR = createSector("Other (Furniture)", sectorK.getId(), 147);
    Sector sectorS = createSector("Outdoor", sectorK.getId(), 148);
    Sector sectorT = createSector("Project furniture", sectorK.getId(), 149);
    Sector sectorU = createSector("Machinery", sectorA.getId(), 15);
    Sector sectorV = createSector("Machinery components", sectorU.getId(), 151);
    Sector sectorW = createSector("Machinery equipment/tools", sectorU.getId(), 152);
    Sector sectorX = createSector("Manufacture of machinery", sectorU.getId(), 153);
    Sector sectorY = createSector("Maritime", sectorU.getId(), 154);
    Sector sectorZ = createSector("Aluminium and steel workboats", sectorY.getId(), 1541);
    Sector sectorAA = createSector("Boat/Yacht building", sectorY.getId(), 1542);
    Sector sectorAB = createSector("Ship repair and conversion", sectorY.getId(), 1543);
    Sector sectorAC = createSector("Metal structures", sectorU.getId(), 155);
    Sector sectorAD = createSector("Other", sectorU.getId(), 156);
    Sector sectorAE = createSector("Repair and maintenance service", sectorU.getId(), 157);
    Sector sectorAF = createSector("Metalworking", sectorA.getId(), 16);
    Sector sectorAG = createSector("Construction of metal structures", sectorAF.getId(), 161);
    Sector sectorAH = createSector("Houses and buildings", sectorAF.getId(), 162);
    Sector sectorAI = createSector("Metal products", sectorAF.getId(), 163);
    Sector sectorAJ = createSector("Metal works", sectorAF.getId(), 164);
    Sector sectorAK = createSector("CNC-machining", sectorAJ.getId(), 1641);
    Sector sectorAL = createSector("Forgings, Fasteners", sectorAJ.getId(), 1642);
    Sector sectorAM = createSector("Gas, Plasma, Laser cutting", sectorAJ.getId(), 1643);
    Sector sectorAN = createSector("MIG, TIG, Aluminum welding", sectorAJ.getId(), 1644);
    Sector sectorAO = createSector("Plastic and Rubber", sectorA.getId(), 17);
    Sector sectorAP = createSector("Packaging", sectorAO.getId(), 171);
    Sector sectorAQ = createSector("Plastic goods", sectorAO.getId(), 172);
    Sector sectorAR = createSector("Plastic processing technology", sectorAO.getId(), 173);
    Sector sectorAS = createSector("Blowing", sectorAR.getId(), 1731);
    Sector sectorAT = createSector("Moulding", sectorAR.getId(), 1732);
    Sector sectorAU = createSector("Plastics welding and processing", sectorAR.getId(), 1733);
    Sector sectorAV = createSector("Plastic profiles", sectorAO.getId(), 174);
    Sector sectorAW = createSector("Printing", sectorA.getId(), 18);
    Sector sectorAX = createSector("Advertising", sectorAW.getId(), 181);
    Sector sectorAY = createSector("Book/Periodicals printing", sectorAW.getId(), 182);
    Sector sectorAZ = createSector("Labelling and packaging printing", sectorAW.getId(), 183);
    Sector sectorBA = createSector("Textile and Clothing", sectorA.getId(), 19);
    Sector sectorBB = createSector("Clothing", sectorBA.getId(), 191);
    Sector sectorBC = createSector("Textile", sectorBA.getId(), 192);
    Sector sectorBD = createSector("Wood", sectorA.getId(), 20);
    Sector sectorBE = createSector("Other (Wood)", sectorBD.getId(), 201);
    Sector sectorBF = createSector("Wooden building materials", sectorBD.getId(), 202);
    Sector sectorBG = createSector("Wooden houses", sectorBD.getId(), 203);
    Sector sectorBH = createSector("Other", null, 2);
    Sector sectorBI = createSector("Creative industries", sectorBH.getId(), 21);
    Sector sectorBJ = createSector("Energy technology", sectorBH.getId(), 22);
    Sector sectorBK = createSector("Environment", sectorBH.getId(), 23);
    Sector sectorBL = createSector("Service", null, 3);
    Sector sectorBM = createSector("Business services", sectorBL.getId(), 31);
    Sector sectorBN = createSector("Engineering", sectorBL.getId(), 32);
    Sector sectorBO = createSector("Information Technology and Telecommunications", sectorBL.getId(), 33);
    Sector sectorBP = createSector("Data processing, Web portals, E-marketing", sectorBO.getId(), 331);
    Sector sectorBQ = createSector("Programming, Consultancy", sectorBO.getId(), 332);
    Sector sectorBR = createSector("Software, Hardware", sectorBO.getId(), 333);
    Sector sectorBS = createSector("Telecommunications", sectorBO.getId(), 334);
    Sector sectorBT = createSector("Tourism", sectorBL.getId(), 34);
    Sector sectorBU = createSector("Translation services", sectorBL.getId(), 35);
    Sector sectorBV = createSector("Transport and Logistics", sectorBL.getId(), 36);
    Sector sectorBW = createSector("Air", sectorBV.getId(), 361);
    Sector sectorBX = createSector("Rail", sectorBV.getId(), 362);
    Sector sectorBY = createSector("Road", sectorBV.getId(), 363);
    Sector sectorBZ = createSector("Water", sectorBV.getId(), 364);
  }

  protected boolean sectorExists(Long sectorId) {
    return entityManager.find(Sector.class, sectorId) != null;
  }

  protected Sector findSectorById(Long id) {
    return sectorService.findById(id);
  }

  protected User createUser(String name, boolean agreeToTerms, List<Sector> sectors, String email, String phoneNumber, Instant dob) {
    return userService.save(new User()
            .setName(name)
            .setAgreeToTerms(agreeToTerms)
            .setEmail(email)
            .setPhoneNumber(phoneNumber)
            .setDob(dob)
            .setSectors(sectors));
  }
  protected User createUser(String name, boolean agreeToTerms, List<Sector> sectors) {
    return userService.save(new User()
            .setName(name)
            .setAgreeToTerms(agreeToTerms)
            .setSectors(sectors));
  }

  public static void clear() {
    createdEntities.clear();
  }

  public static void persistCreatedEntities(EntityManager em) {
    createdEntities.forEach(em::persist);
    em.flush();
  }
}
