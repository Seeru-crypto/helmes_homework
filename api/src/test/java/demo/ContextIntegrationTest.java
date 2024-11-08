package demo;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.model.Sector;
import demo.model.User;
import demo.model.UserFilter;
import demo.service.filter.FieldType;
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
  public static final String USER_NAME_1 = "qJohn Does";
  public static final String USER_NAME_2 = "qJane Does";
  public static final String USER_NAME_3 = "qJack Doesn't";
  public static final String USER_NAME_4 = "qJames Memorial";

  public static final String USER_EMAIL_1 = "johndoe1234@gmail.com";
  public static final String USER_EMAIL_2 = "jane_smith123@example.com";
  public static final String USER_EMAIL_3 = "bob.smith@company.co.uk";
  public static final String USER_EMAIL_4 = "mary.smith@email.com";

  public static final String USER_PHONE_NUMBER_1 = "+123 123456789";
  public static final String USER_PHONE_NUMBER_2 = "+372 1234567";
  public static final String USER_PHONE_NUMBER_3 = "+44 1234567890";
  public static final String USER_PHONE_NUMBER_4 = "+1 1234567890";

  protected Sector createSector(String name, Long parentId, int value) {
    return sectorServiceImpl.save(parentId, name, value);
  }

  protected List<User> createDefaultUsers() {
    List<User> users = new ArrayList<>();
    users.add(createUser(USER_NAME_1, true, List.of(findSectorById(1L)), USER_EMAIL_1, USER_PHONE_NUMBER_1, Instant.parse("1995-04-10T21:00:25.451157400Z"), 140));
    users.add(createUser(USER_NAME_2, true, List.of(findSectorById(2L), findSectorById(11L)), USER_EMAIL_2, USER_PHONE_NUMBER_2,Instant.parse("2000-04-10T21:00:25.451157400Z"), 150));
    users.add(createUser(USER_NAME_3, true, List.of(findSectorById(8L), findSectorById(11L), findSectorById(13L)), USER_EMAIL_3, USER_PHONE_NUMBER_3,Instant.parse("2005-04-10T21:00:25.451157400Z"), 160));
    users.add(createUser(USER_NAME_4, true, List.of(findSectorById(1L), findSectorById(20L)), USER_EMAIL_4, USER_PHONE_NUMBER_4,Instant.parse("2010-04-10T21:00:25.451157400Z"), 150));
    return users;
  }

  protected List<User> createUsersWithoutSectors() {
    List<User> users = new ArrayList<>();
    users.add(createUser(USER_NAME_1, true, List.of(), USER_EMAIL_1, USER_PHONE_NUMBER_1, Instant.parse("1995-04-10T21:00:25.451157400Z"), 152));
    users.add(createUser(USER_NAME_2, true, List.of(), USER_EMAIL_2, USER_PHONE_NUMBER_2,Instant.parse("2000-04-10T21:00:25.451157400Z"), 162));
    users.add(createUser(USER_NAME_3, true, List.of(), USER_EMAIL_3, USER_PHONE_NUMBER_3,Instant.parse("2005-04-10T21:00:25.451157400Z"), 170));
    users.add(createUser(USER_NAME_4, true, List.of(), USER_EMAIL_4, USER_PHONE_NUMBER_4,Instant.parse("2010-04-10T21:00:25.451157400Z"), 190));
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
    return sectorServiceImpl.findById(id);
  }

  protected User createUser(String name, boolean agreeToTerms, List<Sector> sectors, String email, String phoneNumber, Instant dob, int height) {
    return userServiceImpl.save(new User()
            .setName(name)
            .setAgreeToTerms(agreeToTerms)
            .setEmail(email)
            .setPhoneNumber(phoneNumber)
            .setDob(dob)
            .setHeight(height)
            .setSectors(sectors))
            ;
  }
  protected User createUser(String name, boolean agreeToTerms, List<Sector> sectors) {
    return userServiceImpl.save(new User()
            .setName(name)
            .setAgreeToTerms(agreeToTerms)
            .setSectors(sectors));
  }

  protected UserFilter createUserFilter(List<FilterDto> filters, String name, User user) {
    UserFilterDto filterDto = new UserFilterDto().setName(name).setFilters(filters);
    return filterService.saveFilters(filterDto, user.getId());
  }

  protected List<FilterDto> getFilterDtoList() {
    FilterDto filter1 = new FilterDto().setCriteriaValue(AFTER.getCode()).setValue(Instant.now().toString()).setType(FieldType.DATE).setFieldName(DOB);
    FilterDto filter2 = new FilterDto().setCriteriaValue(CONTAINS.getCode()).setValue("value 2").setType(FieldType.STRING).setFieldName(NAME);
    FilterDto filter3 = new FilterDto().setCriteriaValue(SMALLER_THAN.getCode()).setValue("3").setType(FieldType.NUMBER).setFieldName(NAME);
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
