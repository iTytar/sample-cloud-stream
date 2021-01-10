package net.tyt.sample.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * @author Igor Tytar <tytar@mail.ru>
 */
@Component
public class PersonHandler {
    private final PersonService personService;

    @Autowired
    public PersonHandler(PersonService personService) {
        this.personService = personService;
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<Person> person = serverRequest
                .bodyToMono(Person.class)
                .flatMap(personService::create);
        return ServerResponse.created(
                UriComponentsBuilder.fromPath("api/persons/")
                        .build()
                        .toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(person, Person.class));
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        Mono<Person> person = serverRequest
                .bodyToMono(Person.class)
                .flatMap(personService::update);
        return ServerResponse.created(
                UriComponentsBuilder.fromPath("api/persons/")
                        .build()
                        .toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(person, Person.class));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        Mono<Person> person = serverRequest
                .bodyToMono(Person.class)
                .flatMap(personService::delete);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(person, Person.class));
    }
}
