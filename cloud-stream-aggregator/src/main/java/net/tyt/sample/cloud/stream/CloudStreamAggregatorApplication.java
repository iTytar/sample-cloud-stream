package net.tyt.sample.cloud.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class CloudStreamAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamAggregatorApplication.class, args);
    }

    @Bean
    public Consumer<KStream<String,Person>> consume() {
        System.out.println("CONSUMER: init KStream");
//        return input -> input.foreach((k,v) -> System.out.println("CONSUMER: "+v));
        return input -> input.selectKey((o, p) -> p.getId()).toTable(Named.as("person-table"));
    }

}
