package com.ms.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.service.ServiceApplicationTests;
import com.ms.service.dto.ServiceDTO;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceControllerTests extends ServiceApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private MockMvc mockMvc;
    private String id;
    private String name;
    private String category;

    @Autowired
    private ServiceController serviceController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
        this.id = "65f4826c5488840d27e9868e";
        this.name = "empresa1";
        this.category = "category";
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception{
        log.info("testCreateService");
        ServiceDTO serviceDTO = new ServiceDTO(id, "empresa1", "empresa1@email.com","category", false, "descrição empresa1 aqui...", new BigDecimal(2.1), 1, 1, null, null, "sim");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(serviceDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    @Order(2)
    public void testFindAll() throws Exception{
        log.info("testFindAllServices");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @Order(3)
    public void testFindById() throws Exception{
        log.info("testFindById");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getId/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(4)
    public void testFindByName() throws Exception{
        log.info("testFindByServiceName");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getName/" + name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    @Order(5)
    public void testFindByCategory() throws Exception{
        log.info("testFindByServiceCategory");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getCategory/" + category))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").exists());
    }

    @Test
    @Order(6)
    public void testUpdate() throws Exception{
        log.info("testUpdateService");
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/services/" + id)
                .content(asJsonString(new ServiceDTO(id, "telecom2", "empresa2@email.com","category", true, "descrição aqui...",  new BigDecimal(1.2), 1, 1, null, null, "null")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(7)
    public void testDelete() throws Exception{
        log.info("testDeleteService");
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/services/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
    }

    public static String asJsonString(final Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
