package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.controller.dto.SaveUserDto;
import demo.controller.dto.SectorDto;
import demo.controller.dto.UserDto;
import demo.controller.dto.UserFilterDto;
import demo.mapper.SectorMapper;
import demo.service.*;
import demo.service.filter.FilteringLogicService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static demo.ContextIntegrationTest.clear;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Sql("/test-data.sql")
@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected IUserService userServiceImpl;

    @Autowired
    protected FilterService filterService;

    @Autowired
    protected ISectorService sectorServiceImpl;

    @Autowired
    protected SectorMapper sectorMapper;

    @Autowired
    protected FilteringLogicService filteringLogicService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected EntityManager entityManager;

    public <T> List<T> findAll(Class<T> clss) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clss);
        Root<T> rootEntry = cq.from(clss);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
    protected byte[] getBytes( SaveUserDto dto ) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(dto);
    }

    protected byte[] getBytes( UserDto dto ) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(dto);
    }

    protected byte[] getBytes( UserFilterDto dto ) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(dto);
    }

    protected byte[] getBytes( SectorDto dto ) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(dto);
    }

    @BeforeEach
    public void setup() {
        clear();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
}