package l1l.services.impl;

import l1l.models.Spell;
import l1l.services.interf.ISpellService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class SpellService implements ISpellService {

    public Spell setValuesById(Document doc, String id) {
        Spell spell = new Spell();
        StringBuilder str = new StringBuilder();

        String content = Objects.requireNonNull(doc.getElementById(id)).outerHtml();
        Document document = Jsoup.parse(content);

        Elements p = document.select("p");
        Elements ul = document.select("ul");

        spell.setOriginalName(p.get(0).text());
        spell.setSchool(p.get(1).text());

        if(ul.size() > 1) {
            // old layout
            spell.setCastTime(ul.get(0).text());
            spell.setRange(ul.get(1).text());
            spell.setComponents(ul.get(2).text());
            spell.setDuration(ul.get(3).text());
        } else {
            // new layout
            String stringToParse = ul.get(0).text();
            String castTime =   stringToParse.substring(stringToParse.indexOf("Tempo di lancio"), stringToParse.indexOf("Gittata"));
            String range =      stringToParse.substring(stringToParse.indexOf("Gittata"), stringToParse.indexOf("Componenti"));
            String components = stringToParse.substring(stringToParse.indexOf("Componenti"), stringToParse.indexOf("Durata"));
            String duration =   stringToParse.substring(stringToParse.indexOf("Durata"));
            spell.setCastTime(castTime);
            spell.setRange(range);
            spell.setComponents(components);
            spell.setDuration(duration);
        }

        for(int i = 2; i < p.size(); i++) {
            str.append(p.get(i).text());
        }

        spell.setDescription(str.toString());

        return spell;
    }

}
