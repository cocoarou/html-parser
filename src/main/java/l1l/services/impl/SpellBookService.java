package l1l.services.impl;

import l1l.models.SpellBook;
import l1l.services.interf.ISpellBookService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpellBookService implements ISpellBookService {

    public SpellBook setValuesByCssQuery(Document doc, String cssQuery) {
        SpellBook spellBook = new SpellBook();
        List<String> spells = new ArrayList<>();

        Elements elements = doc.select(cssQuery);
        for (Element element : elements) {
            spells.add(element.attr("title")/* + " " +  element.absUrl("href")*/);
        }
        spellBook.setSpells(spells);

        return spellBook;
    }

}
