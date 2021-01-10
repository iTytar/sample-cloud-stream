package net.tyt.sample.cloud.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 *
 * @author Igor Tytar <tytar@mail.ru>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor //(onConstructor=@__(@JsonCreator))  //!!! problem with WebTestClient 
@Builder(toBuilder=true)
public class Person {
    private  UUID id;
    private  String firstName;
    private  String lastName;
}
