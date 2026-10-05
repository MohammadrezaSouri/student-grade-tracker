package model;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private int id;
    private int studentId;
    private int courseId;
    private double score;
    private String semester;

}
