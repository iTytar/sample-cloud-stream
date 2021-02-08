package net.tyt.sample.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {"server.port=0"})
@EmbeddedKafka(count = 1, controlledShutdown = true, topics = {"persons"})
@DirtiesContext
@Slf4j
class CloudStreamConsumerApplicationTests {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private Consumer<Integer, Person> consumer;

    @Autowired
    StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @BeforeEach
    public void before() {
        streamsBuilderFactoryBean.setCloseTimeout(0);
        Map<String, Object> props = KafkaTestUtils.consumerProps("group", "false", embeddedKafka);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "net.tyt.*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<Integer, Person> cf = new DefaultKafkaConsumerFactory<>(props);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "persons");
    }

    @AfterEach
    public void after() {
        consumer.close();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testKafkaStreamsWordCountProcessor() {
        Person expected = Person.builder()
                .id(UUID.randomUUID())
                .firstName("Igor")
                .lastName("Tytar")
                .build();
        Map<String, Object> props = KafkaTestUtils.producerProps(embeddedKafka);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        log.info("sender prop: {}",props);
        DefaultKafkaProducerFactory<Integer, Person> pf = new DefaultKafkaProducerFactory<>(props);
        try {
            KafkaTemplate<Integer, Person> template = new KafkaTemplate<>(pf, true);
            template.setDefaultTopic("persons");
            template.sendDefault(1,expected);
            ConsumerRecords<Integer, Person> cr = KafkaTestUtils.getRecords(consumer);
            assertThat(cr.count()).isEqualTo(1);
        }
        finally {
            pf.destroy();
        }
    }
}
