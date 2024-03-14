package Tempgroup.AWS.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private long id;
    private String name;
    private int age;
    private String dept;
}
