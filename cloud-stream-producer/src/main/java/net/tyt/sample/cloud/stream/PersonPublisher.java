package net.tyt.sample.cloud.stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Component;

@Component
public class PersonPublisher implements Publisher<Person> {
    private Subscriber<? super Person> subscriber;

    @Override
    public void subscribe(Subscriber<? super Person> subscriber) {
        this.subscriber = subscriber;
    }

    public void onNext(Person person) {
        subscriber.onNext(person);
    }
}
