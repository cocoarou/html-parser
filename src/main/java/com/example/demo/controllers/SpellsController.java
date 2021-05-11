package com.example.demo.controllers;

import com.example.demo.models.Spell;
import com.example.demo.models.SpellBook;
import com.example.demo.services.PrintService;
import com.example.demo.services.SpellBookService;
import com.example.demo.services.SpellService;
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

            return printService.separateSpellDetails(s);

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

            Document doc = Jsoup.connect("https://dungeonsanddragons.fandom.com/it/wiki/Tutti_gli_Incantesimi").get();

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
