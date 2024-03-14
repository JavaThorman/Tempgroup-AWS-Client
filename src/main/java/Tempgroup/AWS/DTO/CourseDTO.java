package Tempgroup.AWS.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private long id;
    private String title;
    private String abbreviation;
    private int modules;
    private double fee;
}
