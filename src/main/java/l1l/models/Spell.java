package l1l.models;

import lombok.*;

@ToString
@Data @NoArgsConstructor @AllArgsConstructor
public class Spell {

    private String school;
    private String castTime;
    private String range;
    private String components;
    private String duration;
    private String description;

}
