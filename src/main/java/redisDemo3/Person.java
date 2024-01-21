package redisDemo3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Person")
public class Person implements Serializable {

    @Id
    private Long id;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
}
