package bdtc.lab3.service;

import bdtc.lab3.controller.model.Person;
import bdtc.lab3.dao.TestServiceRepository;
import bdtc.lab3.model.PersonEntity;

import java.util.List;
import java.util.UUID;

public class TestBusinessLogicService {

    private TestServiceRepository testServiceRepository;

    /**
     * Constructs TestBusinessLogicService.
     * @param testServiceRepository TestServiceRepository
     */
    public TestBusinessLogicService(final TestServiceRepository testServiceRepository) {
        this.testServiceRepository = testServiceRepository;
    }

    /**
     * Saves person to repository.
     * @param person Person
     * @return Saved person
     */
    public PersonEntity processCreate(final Person person) {
        PersonEntity personEntity = new PersonEntity(person.getName());
        testServiceRepository.save(personEntity);
        return personEntity;
    }

    /**
     * Retrieves person from repository by id.
     * @param id Person id
     * @return Person
     */
    public PersonEntity processGet(final String id) {
        return testServiceRepository.get(UUID.fromString(id));
    }

    /**
     * Retrieves all persons from repository.
     * @return List of persons
     */
    public List<PersonEntity> processGetAll() {
        return testServiceRepository.getAll();
    }
}
