package net.tyt.sample.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * @author Igor Tytar <tytar@mail.ru>
 */
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "10000")
@EmbeddedKafka
@Import(TestChannelBinderConfiguration.class)
@DirtiesContext
@Slf4j
class PersonRoadTests {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Autowired
    private WebTestClient client;
    
    @Test
    void test01_Create() {
        Person person = Person.builder()
                .firstName("Igor")
                .lastName("Tytar")
                .build();
        client.post()
                .uri("/api/persons")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(person), Person.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Person.class)
                .value(p -> {
                            Assertions.assertNotNull(p.getId());
                            Assertions.assertEquals("Igor", p.getFirstName());
                            Assertions.assertEquals("Tytar", p.getLastName());
                        }
                );
        String result = new String(output.receive().getPayload());
        Assertions.assertTrue(result.matches(".*Tytar.*"));
        Assertions.assertTrue(result.matches(".*Igor.*"));
        log.debug("############ {}",result);
    }
}
