package net.tyt.sample.cloud.stream;

import com.jcabi.aspects.Loggable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class CloudStreamProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamProcessorApplication.class, args);
    }

    @Bean
    public static Function<Flux<Person>,Flux<Customer>> process() {
        return persons -> persons.map(CloudStreamProcessorApplication::createCustomer);

    }
    
    @Loggable(prepend=true)
    private static Customer createCustomer(Person person) {
        return Customer.builder()
                    .id(UUID.randomUUID())
                    .personId(person.getId())
                    .accountId(UUID.randomUUID())
                    .build();
    }

}
