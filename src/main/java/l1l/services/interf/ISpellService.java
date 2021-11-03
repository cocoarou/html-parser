package l1l.services.interf;

import l1l.models.Spell;
import org.jsoup.nodes.Document;

public interface ISpellService {
    Spell setValuesById(Document doc, String id);
}
