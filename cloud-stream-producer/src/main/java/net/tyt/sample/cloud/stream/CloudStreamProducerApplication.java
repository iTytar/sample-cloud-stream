package net.tyt.sample.cloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@SpringBootApplication
public class CloudStreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamProducerApplication.class, args);
    }

    @Bean
    public Supplier<Flux<Person>> supply(PersonPublisher publisher) {
        return () -> Flux.from(publisher);
    }

}
