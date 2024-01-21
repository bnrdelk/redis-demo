package redisDemo2;

import redisDemo2.Person;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PersonService implements IPersonService{

    private static final String PERSON_KEY = "Person";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, Person> hashOperations;

    public PersonService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // get ready the hash operations on init
    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    /* CRUD methods */

    @Override
    public void save(Person person) {
        try {
            log.info("Saving person with ID: {}", person.getId());
            hashOperations.put(PERSON_KEY, person.getId(), person);
            log.info("Person saved successfully.");
        } catch (Exception e) {
            log.error("Error while saving person", e);

        }
    }

    @Override
    public void update(Person person) {
        hashOperations.put(PERSON_KEY, person.getId(), person);
    }

    @Override
    public void delete(Long id) {
        hashOperations.delete(PERSON_KEY, id);
    }

    @Override
    public Person findById(Long id) {
        return hashOperations.get(PERSON_KEY, id);
    }

    @Override
    public List<Person> getAllPeople(String key) {
        return hashOperations.values(key);
    }

    @Override
    public Map<Long, Person> getAll() {
        return hashOperations.entries(PERSON_KEY);
    }
}
