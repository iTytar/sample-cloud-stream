package net.tyt.sample.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Igor Tytar <tytar@mail.ru>
 */
@Service
@Slf4j
public class PersonService {

    private final PersonPublisher personPublisher;

    @Autowired
    public PersonService(PersonPublisher publisher) {
        this.personPublisher = publisher;
    }

    public Mono<Person> create(final Person person) {
        return publish(person.toBuilder().id(UUID.randomUUID()).build());
    }

    public Mono<Person> update(final Person person) {
        return publish(person);
    }

    public Mono<Person> delete(final Person person) {
        return publish(person.toBuilder()
                .firstName("")
                .lastName("")
                .build());
    }

    private Mono<Person> publish(final Person person) {
        log.info("publish({})...", person);
        personPublisher.onNext(person);
        return Mono.just(person);
    }
}
