package net.tyt.sample.cloud.stream;

import com.jcabi.aspects.Loggable;
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
    public PersonService(final PersonPublisher publisher) {
        this.personPublisher = publisher;
    }

    @Loggable(prepend = true)
    public Mono<Person> create(final Person person) {
        return publish(person.toBuilder().id(UUID.randomUUID()).build());
    }

    @Loggable(prepend = true)
    public Mono<Person> update(final Person person) {
        return publish(person);
    }

    @Loggable(prepend = true)
    public Mono<Person> delete(final Person person) {
        return publish(person.toBuilder()
                .firstName("")
                .lastName("")
                .build());
    }

    private Mono<Person> publish(final Person person) {
        personPublisher.onNext(person);
        return Mono.just(person);
    }
}
