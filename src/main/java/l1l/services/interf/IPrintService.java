package l1l.services.interf;

import l1l.models.Spell;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface IPrintService {
    @Deprecated
    String separateSpellDetails(Spell spell);
    String separateSpellDetailsStringBuilder(Spell spell, String spellName);

    String printSpellDetails(ResponseEntity<String> response) throws JSONException, IOException;

}
