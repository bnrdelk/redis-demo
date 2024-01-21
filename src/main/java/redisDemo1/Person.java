package redisDemo1;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@RedisHash("People")
public class Person implements Serializable {

    @Id
    private String id;
    private String name;
    public Person(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
    }
}
