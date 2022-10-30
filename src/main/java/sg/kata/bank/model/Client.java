package sg.kata.bank.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Client {

    private Long id;
    private String firstName;
    private String lastName;
}
