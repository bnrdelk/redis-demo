package redisDemo2;

import java.util.List;
import java.util.Map;

public interface IPersonService {

    void save(Person person);
    void update(Person person);
    void delete(Long id);
    Person findById(Long id);
    List<Person> getAllPeople(String key);
    Map<Long,Person> getAll();

}
