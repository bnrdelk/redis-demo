package redisDemo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisDemo1Application {

	@Autowired
	static PeopleRepository peopleRepository;

	public static void main(String[] args) {
		SpringApplication.run(RedisDemo1Application.class, args);
	}

	@Bean
	public ApplicationRunner runner(PeopleRepository peopleRepository){
		return args -> {
			Person person = new Person(
					"1204", "name1");

			peopleRepository.save(person);
			Person foundPerson = peopleRepository.findById("1204").get();

			System.out.println(foundPerson);
		};
	}
}
