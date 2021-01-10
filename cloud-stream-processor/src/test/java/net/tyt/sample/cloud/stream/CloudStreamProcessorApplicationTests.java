package net.tyt.sample.cloud.stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka
@Import(TestChannelBinderConfiguration.class)
@DirtiesContext
class CloudStreamProcessorApplicationTests {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Test
    void contextLoads() {
    }

    @Test
    void test01_Process() {
        Person person = Person.builder()
                .id(UUID.randomUUID())
                .firstName("Igor")
                .lastName("Tytar")
                .build();
        input.send(new GenericMessage<>(person));
        String result = new String(output.receive().getPayload());
        assertThat(result)
                .matches(".*\"personId\":\""+person.getId()+"\".*");
    }

}
