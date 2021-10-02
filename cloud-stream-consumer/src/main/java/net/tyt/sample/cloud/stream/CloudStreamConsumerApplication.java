package net.tyt.sample.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class CloudStreamConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamConsumerApplication.class, args);
    }

    @Bean
    public Consumer<Flux<Customer>> consume() {
        return consumers -> consumers.subscribe(customer -> log.info("consume({})...",customer));
    }

}
