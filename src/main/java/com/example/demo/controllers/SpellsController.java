package com.example.demo.controllers;

import com.example.demo.models.Spell;
import com.example.demo.models.SpellBook;
import com.example.demo.services.impl.PrintService;
import com.example.demo.services.impl.SpellBookService;
import com.example.demo.services.impl.SpellService;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class SpellsController {

    @Autowired
    private PrintService printService;

    @Autowired
    private SpellService spellService;

    @Autowired
    private SpellBookService spellBookService;

    // begin custom controllers for spells with a slash in the name
    @RequestMapping(value = "/spells/Antipatia/Simpatia", method = RequestMethod.GET)
    @ResponseBody
    public String antipatiaSimpatia() {

        String spell = "Antipatia/Simpatia";

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Document doc = Jsoup.connect("https://dungeonsanddragons.fandom.com/it/wiki/" + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
            String json = objectMapper.writer(prettyPrinter).writeValueAsString(s);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells/Cecità/Sordità", method = RequestMethod.GET)
    @ResponseBody
    public String cecitaSordita() {

        String spell = "Cecità/Sordità";

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Document doc = Jsoup.connect("https://dungeonsanddragons.fandom.com/it/wiki/" + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
            String json = objectMapper.writer(prettyPrinter).writeValueAsString(s);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells/Ingrandire/Ridurre", method = RequestMethod.GET)
    @ResponseBody
    public String ingrandireRidurre() {

        String spell = "Ingrandire/Ridurre";

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Document doc = Jsoup.connect("https://dungeonsanddragons.fandom.com/it/wiki/" + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
            String json = objectMapper.writer(prettyPrinter).writeValueAsString(s);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }
    // end custom controllers for spells with a slash in the name

    @RequestMapping(value = "/spells/{spell}", method = RequestMethod.GET)
    @ResponseBody
    public String spell(@PathVariable (name = "spell") String spell) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Document doc = Jsoup.connect("https://dungeonsanddragons.fandom.com/it/wiki/" + spell).get();

            Spell s = spellService.setValuesById(doc, "mw-content-text");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
            String json = objectMapper.writer(prettyPrinter).writeValueAsString(s);

            return printService.separateSpellDetailsStringBuilder(s, spell);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells", method = RequestMethod.GET)
    @ResponseBody
    public String spellsList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Document doc = Jsoup.connect("https://dungeonsanddragons.fandom.com/it/wiki/Tutti_Gli_Incantesimi_del_Gioco").get();

            SpellBook spellBook = spellBookService.setValuesByCssQuery(doc, "table td a");

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
            String json = objectMapper.writer(prettyPrinter).writeValueAsString(spellBook);

            return json;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "errore";
    }

}
