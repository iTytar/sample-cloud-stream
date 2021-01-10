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
class CloudStreamConsumerApplicationTests {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Test
    void contextLoads() {
    }

    @Test
    void test01_Consume() {
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .personId(UUID.randomUUID())
                .accountId(UUID.randomUUID())
                .build();
        input.send(new GenericMessage<>(customer));
    }

}
