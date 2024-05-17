package bdtc.lab3.service;

import bdtc.lab3.controller.model.Person;
import bdtc.lab3.dao.TestServiceRepository;
import bdtc.lab3.model.PersonEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestBusinessLogicServiceTest.TestBusinessLogicServiceTestConfiguration.class })
public class TestBusinessLogicServiceTest {

    private static final UUID ID1 = UUID.randomUUID();
    private static final UUID ID2 = UUID.randomUUID();


    @Autowired
    private TestBusinessLogicService testBusinessLogicService;

    @Autowired
    private TestServiceRepository testServiceRepository;

    @Test
    public void testCreate() {
        // create
        Person person = new Person("test");

        PersonEntity personEntity = testBusinessLogicService.processCreate(person);

        Assert.assertEquals(person.getName(), personEntity.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).save(personEntity);
    }

    @Test
    public void testGet() {
        // get all
        UUID uuid = UUID.randomUUID();
        PersonEntity personEntity = testBusinessLogicService.processGet(uuid.toString());

        Assert.assertEquals("name", personEntity.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).get(uuid);
    }

    @Test
    public void testDelete() {
        // get all
        PersonEntity personEntity = testBusinessLogicService.processGet(ID1.toString());

        // delete
        testBusinessLogicService.processDelete(personEntity.getId().toString());
        Mockito.verify(testServiceRepository, Mockito.times(1)).get(ID1);

        Mockito.verify(testServiceRepository, Mockito.times(1)).delete(personEntity.getId());
    }

    @Test
    public void testUpdate() {
        // get all
        PersonEntity personEntity = testBusinessLogicService.processGet(ID2.toString());

        // update
        personEntity = testBusinessLogicService.processUpdate(personEntity.getId().toString(),
                new Person("test_upd"));

        Assert.assertEquals("test_upd", personEntity.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).get(ID2);

        Mockito.verify(testServiceRepository, Mockito.times(1)).update(personEntity);
    }

    @Test
    public void testGetAll() {
        // get all
        List<PersonEntity> personEntityList = testBusinessLogicService.processGetAll();

        Assert.assertEquals("name1", personEntityList.get(0).getName());
        Assert.assertEquals("name2", personEntityList.get(1).getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).getAll();
    }

    @Configuration
    static class TestBusinessLogicServiceTestConfiguration {

        @Bean
        TestServiceRepository testServiceRepository() {

            TestServiceRepository testServiceRepository = mock(TestServiceRepository.class);
            when(testServiceRepository.get(ID1))
                    .thenReturn(new PersonEntity("deleting"));
            when(testServiceRepository.get(ID2))
                    .thenReturn(new PersonEntity("updating"));
            when(testServiceRepository.get(any())).thenReturn(new PersonEntity("name"));
            when(testServiceRepository.getAll())
                    .thenReturn(Arrays.asList(new PersonEntity("name1"), new PersonEntity("name2")));
            return testServiceRepository;
        }

        @Bean
        TestBusinessLogicService testBusinessLogicService(TestServiceRepository testServiceRepository) {
            return new TestBusinessLogicService(testServiceRepository);
        }
    }

}
