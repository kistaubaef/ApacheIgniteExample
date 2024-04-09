package bdtc.lab3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import bdtc.lab3.config.ServiceConf;
import bdtc.lab3.config.UtilsConf;
import bdtc.lab3.controller.model.Person;
import bdtc.lab3.model.PersonEntity;
import bdtc.lab3.utils.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ServiceConf.class, UtilsConf.class })
public class TestServiceControllerTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityUtils entityUtils;

    @Before
    public void init() {
        entityUtils.clearPersonEntitiesCache();
    }

    @Test
    public void get_should_returnPersonEntity_when_personEntityExists() throws Exception {
        PersonEntity personEntity = entityUtils.createAndSavePersonEntity();
        String expectedJson = objectMapper.writeValueAsString(personEntity);

        mvc.perform(get("/person/get/" + personEntity.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void getAll_should_returnListOfPersonEntities_when_personEntitiesExist() throws Exception {
        // Arrange
        int numberOfEntities = 5;
        List<PersonEntity> personEntities = new ArrayList<>();
        for (int i = 0; i < numberOfEntities; i++) {
            personEntities.add(entityUtils.createAndSavePersonEntity());
        }
        String expectedJson = objectMapper.writeValueAsString(personEntities);

        // Act and Assert
        mvc.perform(get("/person/get/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void delete_should_returnNoContent_when_personEntityIsDeleted() throws Exception {
        // Arrange
        PersonEntity personEntity = entityUtils.createAndSavePersonEntity();

        // Act and Assert
        mvc.perform(delete("/person/delete/" + personEntity.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void create_should_returnCreatedPersonEntity_when_personEntityIsCreated() throws Exception {
        // Arrange
        Person person = new Person();
        // set the properties of the person object
        String expectedJson = objectMapper.writeValueAsString(person);

        // Act and Assert
        mvc.perform(post("/person/create").contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update_should_returnUpdatedPersonEntity_when_personEntityIsUpdated() throws Exception {
        // Arrange
        PersonEntity personEntity = entityUtils.createAndSavePersonEntity();
        Person person = new Person();
        // set the properties of the person object
        String expectedJson = objectMapper.writeValueAsString(person);

        // Act and Assert
        mvc.perform(put("/person/update/" + personEntity.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
