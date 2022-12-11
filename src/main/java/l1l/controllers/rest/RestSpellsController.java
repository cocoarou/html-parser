package l1l.controllers.rest;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import l1l.models.Spell;
import l1l.models.SpellBook;
import l1l.services.interf.IPrintService;
import l1l.services.interf.ISpellBookService;
import l1l.services.interf.ISpellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class RestSpellsController {

    @Value("${api.url}")
    private String URL;
    @Value("${api.url.spells}")
    private String URL_SPELLS;
    @Autowired
    private IPrintService printService;
    @Autowired
    private ISpellService spellService;
    @Autowired
    private ISpellBookService spellBookService;

    @RequestMapping(value = "/spells/{spell}", method = RequestMethod.GET)
    public Map spell(@PathVariable(name = "spell") String spell) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(URL_SPELLS + spell), String.class);
            Map<String, Spell> resultMap = new HashMap<>();
            resultMap.put("spell", spellService.returnSpellValues(response));
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap();
    }

    @RequestMapping(value = "/spells", method = RequestMethod.GET)
    public Map spellsList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(URL_SPELLS), String.class);

            SpellBook spellBook = spellBookService.setValuesByApiCall(response);
            Map<String, SpellBook> resultMap = new HashMap<>();
            resultMap.put("spells", spellBook);

            return resultMap;

        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return new HashMap();
    }

}
