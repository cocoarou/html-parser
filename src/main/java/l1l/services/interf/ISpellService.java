package l1l.services.interf;

import l1l.models.Spell;
import org.jsoup.nodes.Document;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;

public interface ISpellService {
    Spell setValuesById(Document doc, String id);
    Spell returnSpellValues(ResponseEntity<String> response) throws JSONException;
}
