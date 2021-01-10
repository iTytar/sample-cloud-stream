package net.tyt.sample.cloud.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 *
 * @author Igor Tytar <tytar@mail.ru>
 */
@Configuration
public class PersonRoute {
    @Bean
    public RouterFunction<ServerResponse> routePerson(PersonHandler personHandler) {
        return route(POST("/api/persons"),personHandler::create)
                .and(route(PUT("/api/persons/{id}"),personHandler::update))
                .and(route(DELETE("/api/persons/{id}"),personHandler::delete));
    }
    
}
