package l1l.models;

import lombok.*;
import java.util.List;

@ToString
@Data @NoArgsConstructor @AllArgsConstructor
public class SpellBook {

    private List<String> spells;

}
