package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.SectorEntity;
import demo.model.User;
import demo.model.UserFilter;
import demo.service.filter.DataTypes;
import jakarta.persistence.EntityManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static demo.service.filter.DateCriteria.AFTER;
import static demo.service.filter.NumberCriteria.SMALLER_THAN;
import static demo.service.filter.StringCriteria.CONTAINS;
import static demo.service.filter.UserFieldNames.DOB;
import static demo.service.filter.UserFieldNames.NAME;

public class ContextIntegrationTest extends BaseIntegrationTest {

  private static final List<Object> createdEntities = new ArrayList<>();

  protected SectorEntity createSector(String name, Long parentId, int value) {
    return sectorService.save(parentId, name, value);
  }

  protected List<User> createDefaultUsers() {
    List<User> users = new ArrayList<>();
    users.add(createUser("qJohn Does", true, List.of(findSectorById(1L)), "johndoe1234@gmail.com", "+123 123456789", Instant.parse("1995-04-10T21:00:25.451157400Z")));
    users.add(createUser("qJane Does", true, List.of(findSectorById(2L), findSectorById(11L)), "jane_smith123@example.com", "+372 1234567",Instant.parse("2000-04-10T21:00:25.451157400Z")));
    users.add(createUser("qJack Doesn't", true, List.of(findSectorById(8L), findSectorById(11L), findSectorById(13L)), "bob.smith@company.co.uk", "+44 1234567890",Instant.parse("2005-04-10T21:00:25.451157400Z")));
    users.add(createUser("qJames Memorial", true, List.of(findSectorById(1L), findSectorById(20L)), "mary.smith@email.com", "+1 1234567890",Instant.parse("2010-04-10T21:00:25.451157400Z")));
    return users;
  }

  protected List<User> createUsersWithoutSectors() {
    List<User> users = new ArrayList<>();
    users.add(createUser("qJohn Does", true, List.of(), "johndoe1234@gmail.com", "+123 123456789", Instant.parse("1995-04-10T21:00:25.451157400Z")));
    users.add(createUser("qJane Does", true, List.of(), "jane_smith123@example.com", "+372 1234567",Instant.parse("2000-04-10T21:00:25.451157400Z")));
    users.add(createUser("qJack Doesn't", true, List.of(), "bob.smith@company.co.uk", "+44 1234567890",Instant.parse("2005-04-10T21:00:25.451157400Z")));
    users.add(createUser("qJames Memorial", true, List.of(), "mary.smith@email.com", "+1 1234567890",Instant.parse("2010-04-10T21:00:25.451157400Z")));
    return users;
  }

  protected void createFullSectorTree() {
    SectorEntity sectorA = createSector("Manufacturing", null, 1);
    SectorEntity sectorB = createSector("Construction materials", sectorA.getId(), 11);
    SectorEntity sectorC = createSector("Electronics and Optics", sectorA.getId(), 12);
    SectorEntity sectorD = createSector("Food and Beverage", sectorA.getId(), 13);
    SectorEntity sectorE = createSector("Bakery & confectionery products", sectorD.getId(), 131);
    SectorEntity sectorF = createSector("Beverages", sectorD.getId(), 132);
    SectorEntity sectorG = createSector("Fish & fish products", sectorD.getId(), 133);
    SectorEntity sectorH = createSector("Milk & dairy products", sectorD.getId(), 134);
    SectorEntity sectorI = createSector("Other", sectorD.getId(), 135);
    SectorEntity sectorJ = createSector("Sweets & snack food", sectorD.getId(), 136);
    SectorEntity sectorK = createSector("Furniture", sectorA.getId(), 14);
    SectorEntity sectorL = createSector("Bathroom/sauna", sectorK.getId(), 141);
    SectorEntity sectorM = createSector("Bedroom", sectorK.getId(), 142);
    SectorEntity sectorN = createSector("Children’s room", sectorK.getId(), 143);
    SectorEntity sectorO = createSector("Kitchen", sectorK.getId(), 144);
    SectorEntity sectorP = createSector("Living room", sectorK.getId(), 145);
    SectorEntity sectorQ = createSector("Office", sectorK.getId(), 146);
    SectorEntity sectorR = createSector("Other (Furniture)", sectorK.getId(), 147);
    SectorEntity sectorS = createSector("Outdoor", sectorK.getId(), 148);
    SectorEntity sectorT = createSector("Project furniture", sectorK.getId(), 149);
    SectorEntity sectorU = createSector("Machinery", sectorA.getId(), 15);
    SectorEntity sectorV = createSector("Machinery components", sectorU.getId(), 151);
    SectorEntity sectorW = createSector("Machinery equipment/tools", sectorU.getId(), 152);
    SectorEntity sectorX = createSector("Manufacture of machinery", sectorU.getId(), 153);
    SectorEntity sectorY = createSector("Maritime", sectorU.getId(), 154);
    SectorEntity sectorZ = createSector("Aluminium and steel workboats", sectorY.getId(), 1541);
    SectorEntity sectorAA = createSector("Boat/Yacht building", sectorY.getId(), 1542);
    SectorEntity sectorAB = createSector("Ship repair and conversion", sectorY.getId(), 1543);
    SectorEntity sectorAC = createSector("Metal structures", sectorU.getId(), 155);
    SectorEntity sectorAD = createSector("Other", sectorU.getId(), 156);
    SectorEntity sectorAE = createSector("Repair and maintenance service", sectorU.getId(), 157);
    SectorEntity sectorAF = createSector("Metalworking", sectorA.getId(), 16);
    SectorEntity sectorAG = createSector("Construction of metal structures", sectorAF.getId(), 161);
    SectorEntity sectorAH = createSector("Houses and buildings", sectorAF.getId(), 162);
    SectorEntity sectorAI = createSector("Metal products", sectorAF.getId(), 163);
    SectorEntity sectorAJ = createSector("Metal works", sectorAF.getId(), 164);
    SectorEntity sectorAK = createSector("CNC-machining", sectorAJ.getId(), 1641);
    SectorEntity sectorAL = createSector("Forgings, Fasteners", sectorAJ.getId(), 1642);
    SectorEntity sectorAM = createSector("Gas, Plasma, Laser cutting", sectorAJ.getId(), 1643);
    SectorEntity sectorAN = createSector("MIG, TIG, Aluminum welding", sectorAJ.getId(), 1644);
    SectorEntity sectorAO = createSector("Plastic and Rubber", sectorA.getId(), 17);
    SectorEntity sectorAP = createSector("Packaging", sectorAO.getId(), 171);
    SectorEntity sectorAQ = createSector("Plastic goods", sectorAO.getId(), 172);
    SectorEntity sectorAR = createSector("Plastic processing technology", sectorAO.getId(), 173);
    SectorEntity sectorAS = createSector("Blowing", sectorAR.getId(), 1731);
    SectorEntity sectorAT = createSector("Moulding", sectorAR.getId(), 1732);
    SectorEntity sectorAU = createSector("Plastics welding and processing", sectorAR.getId(), 1733);
    SectorEntity sectorAV = createSector("Plastic profiles", sectorAO.getId(), 174);
    SectorEntity sectorAW = createSector("Printing", sectorA.getId(), 18);
    SectorEntity sectorAX = createSector("Advertising", sectorAW.getId(), 181);
    SectorEntity sectorAY = createSector("Book/Periodicals printing", sectorAW.getId(), 182);
    SectorEntity sectorAZ = createSector("Labelling and packaging printing", sectorAW.getId(), 183);
    SectorEntity sectorBA = createSector("Textile and Clothing", sectorA.getId(), 19);
    SectorEntity sectorBB = createSector("Clothing", sectorBA.getId(), 191);
    SectorEntity sectorBC = createSector("Textile", sectorBA.getId(), 192);
    SectorEntity sectorBD = createSector("Wood", sectorA.getId(), 20);
    SectorEntity sectorBE = createSector("Other (Wood)", sectorBD.getId(), 201);
    SectorEntity sectorBF = createSector("Wooden building materials", sectorBD.getId(), 202);
    SectorEntity sectorBG = createSector("Wooden houses", sectorBD.getId(), 203);
    SectorEntity sectorBH = createSector("Other", null, 2);
    SectorEntity sectorBI = createSector("Creative industries", sectorBH.getId(), 21);
    SectorEntity sectorBJ = createSector("Energy technology", sectorBH.getId(), 22);
    SectorEntity sectorBK = createSector("Environment", sectorBH.getId(), 23);
    SectorEntity sectorBL = createSector("Service", null, 3);
    SectorEntity sectorBM = createSector("Business services", sectorBL.getId(), 31);
    SectorEntity sectorBN = createSector("Engineering", sectorBL.getId(), 32);
    SectorEntity sectorBO = createSector("Information Technology and Telecommunications", sectorBL.getId(), 33);
    SectorEntity sectorBP = createSector("Data processing, Web portals, E-marketing", sectorBO.getId(), 331);
    SectorEntity sectorBQ = createSector("Programming, Consultancy", sectorBO.getId(), 332);
    SectorEntity sectorBR = createSector("Software, Hardware", sectorBO.getId(), 333);
    SectorEntity sectorBS = createSector("Telecommunications", sectorBO.getId(), 334);
    SectorEntity sectorBT = createSector("Tourism", sectorBL.getId(), 34);
    SectorEntity sectorBU = createSector("Translation services", sectorBL.getId(), 35);
    SectorEntity sectorBV = createSector("Transport and Logistics", sectorBL.getId(), 36);
    SectorEntity sectorBW = createSector("Air", sectorBV.getId(), 361);
    SectorEntity sectorBX = createSector("Rail", sectorBV.getId(), 362);
    SectorEntity sectorBY = createSector("Road", sectorBV.getId(), 363);
    SectorEntity sectorBZ = createSector("Water", sectorBV.getId(), 364);
  }

  protected boolean sectorExists(Long sectorId) {
    return entityManager.find(SectorEntity.class, sectorId) != null;
  }

  protected SectorEntity findSectorById(Long id) {
    return sectorService.findById(id);
  }

  protected User createUser(String name, boolean agreeToTerms, List<SectorEntity> sectors, String email, String phoneNumber, Instant dob) {
    return userService.save(new User()
            .setName(name)
            .setAgreeToTerms(agreeToTerms)
            .setEmail(email)
            .setPhoneNumber(phoneNumber)
            .setDob(dob)
            .setSectors(sectors));
  }
  protected User createUser(String name, boolean agreeToTerms, List<SectorEntity> sectors) {
    return userService.save(new User()
            .setName(name)
            .setAgreeToTerms(agreeToTerms)
            .setSectors(sectors));
  }

  protected UserFilter createUserFilter(List<FilterDto> filters, String name, User user) {
    UserFilterDto filterDto = new UserFilterDto().setName(name).setFilters(filters);
    return filterService.saveFilters(filterDto, user.getId());
  }

  protected List<FilterDto> getFilterDtoList() {
    FilterDto filter1 = new FilterDto().setCriteria(AFTER.getKood()).setValue(Instant.now().toString()).setType(DataTypes.DATE).setFieldName(DOB);
    FilterDto filter2 = new FilterDto().setCriteria(CONTAINS.getKood()).setValue("value 2").setType(DataTypes.STRING).setFieldName(NAME);
    FilterDto filter3 = new FilterDto().setCriteria(SMALLER_THAN.getKood()).setValue("3").setType(DataTypes.NUMBER).setFieldName(NAME);
    return List.of(filter2, filter1, filter3);
  }

  public static void clear() {
    createdEntities.clear();
  }

  public static void persistCreatedEntities(EntityManager em) {
    createdEntities.forEach(em::persist);
    em.flush();
  }
}
