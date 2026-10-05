package model;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private int id;
    private int unit;
    private String name;
}
