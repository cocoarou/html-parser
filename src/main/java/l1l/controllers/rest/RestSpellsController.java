package l1l.controllers.rest;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import l1l.models.Spell;
import l1l.models.SpellBook;
import l1l.services.interf.IPrintService;
import l1l.services.interf.ISpellBookService;
import l1l.services.interf.ISpellService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RestSpellsController {

    private final static String URL = "https://dungeonsanddragons.fandom.com/it/wiki/";
    @Autowired
    private IPrintService printService;
    @Autowired
    private ISpellService spellService;
    @Autowired
    private ISpellBookService spellBookService;

    // begin custom controllers for spells with a slash in the name
    @RequestMapping(value = "/spells/Antipatia/Simpatia", method = RequestMethod.GET)
    public String antipatiaSimpatia() {

        String spell = "Antipatia/Simpatia";

        try {
            Document doc = Jsoup.connect(URL + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells/Cecità/Sordità", method = RequestMethod.GET)
    public String cecitaSordita() {

        String spell = "Cecità/Sordità";

        try {
            Document doc = Jsoup.connect(URL + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells/Ingrandire/Ridurre", method = RequestMethod.GET)
    public String ingrandireRidurre() {

        String spell = "Ingrandire/Ridurre";

        try {
            Document doc = Jsoup.connect(URL + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }
    // end custom controllers for spells with a slash in the name

    @RequestMapping(value = "/spells/{spell}", method = RequestMethod.GET)
    public String spell(@PathVariable(name = "spell") String spell) {

        try {
            Document doc = Jsoup.connect(URL + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells", method = RequestMethod.GET)
    public String spellsList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Document doc = Jsoup.connect(URL + "Tutti_Gli_Incantesimi_del_Gioco").get();

            SpellBook spellBook = spellBookService.setValuesByCssQuery(doc, "table td a");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            return objectMapper.writer(prettyPrinter).writeValueAsString(spellBook);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

}
