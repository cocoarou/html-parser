package l1l.services.interf;

import l1l.models.SpellBook;
import org.jsoup.nodes.Document;

public interface ISpellBookService {
    SpellBook setValuesByCssQuery(Document doc, String cssQuery);
}
