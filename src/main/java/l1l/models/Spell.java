package l1l.models;

import lombok.*;

import java.util.Map;

@ToString
@Data @NoArgsConstructor @AllArgsConstructor
public class Spell {

    private String school;
    private String castTime;
    private String range;
    private String components;
    private String duration;
    private String description;
    private String higherLevel;
    private String originalName;
    private Boolean concentration;
    private Boolean ritual;
    private String material;
    private Integer level;
    private String damageType;
    private Map<String, String> damageAtSlotLevel;

}
