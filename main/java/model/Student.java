package model;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String studentNumber;

}
