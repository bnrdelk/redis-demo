package redisDemo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /* CRUD methods */
    @PostMapping(value = "/save")
        public ResponseEntity<Object> save(@RequestBody Person person){
            try {
                personService.save(person);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (Exception e) {
                log.error("ERROR: Save failed!");
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }

    @PostMapping(value = "update")
    public ResponseEntity<Object> update(@RequestBody Person person){
        try {
            Person person1 = personService.findById(person.getId());
            if(person1 != null){
                personService.update(person);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("ERROR: Update failed!");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam("id") Long id){
        try {
            personService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR: Delete failed!");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/person/id")
    public ResponseEntity<Object> getById(@RequestParam("id") Long id){
        try {
            return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR: Find by id failed!");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/person/list")
    public ResponseEntity<Object> listAllPeople() {
        try {
            return new ResponseEntity<>(personService.getAllPeople("Person"), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR: List all failed!", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/person/all")
    public ResponseEntity<Object> listAll() {
        try {
            Map<Long, Person> personMap = personService.getAll();
            return new ResponseEntity<>(personMap, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR: List all failed!", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
