package bdtc.lab3.controller;

import bdtc.lab3.controller.model.Person;
import bdtc.lab3.model.PersonEntity;
import bdtc.lab3.service.TestBusinessLogicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class TestServiceController {

    private TestBusinessLogicService testBusinessLogicService;

    /**
     * Constructs TestServiceController.
     * @param testBusinessLogicService business logic service
     */
    public TestServiceController(final TestBusinessLogicService testBusinessLogicService) {
        this.testBusinessLogicService = testBusinessLogicService;
    }

    /**
     * POST /create.
     * @param person Person
     * @return Response with Person
     */
    @PostMapping(
        path = {"/create"},
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonEntity> createPerson(@RequestBody final Person person) {
        PersonEntity personEntity = testBusinessLogicService.processCreate(person);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

    /**
     * GET /get/{id}.
     * @param id Person id
     * @return Response with Person
     */
    @GetMapping(path = {"/get/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonEntity> getPerson(@PathVariable final String id) {
        PersonEntity personEntity = testBusinessLogicService.processGet(id);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

    /**
     * GET /get/all.
     * @return Response with a list of all persons
     */
    @GetMapping(path = {"/get/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonEntity>> getAll() {
        List<PersonEntity> personEntities = testBusinessLogicService.processGetAll();
        return new ResponseEntity<>(personEntities, HttpStatus.OK);
    }


    /**
     * DELETE /delete/{id}.
     * @param id Person id
     * @return Response with status code
     */
    @DeleteMapping(path = {"/delete/{id}"})
    public ResponseEntity<Void> deletePerson(@PathVariable final String id) {
        testBusinessLogicService.processDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT /update/{id}.
     * @param id Person id
     * @param person Person
     * @return Response with status code
    **/
    @PutMapping(
        path = {"/update/{id}"},
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonEntity> updatePerson(@PathVariable final String id, @RequestBody final Person person) {
        testBusinessLogicService.processUpdate(id, person);
        PersonEntity personEntity = testBusinessLogicService.processGet(id);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }
}
