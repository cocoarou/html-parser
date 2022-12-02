package l1l.controllers.rest;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import l1l.models.SpellBook;
import l1l.services.interf.IPrintService;
import l1l.services.interf.ISpellBookService;
import l1l.services.interf.ISpellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
    public String spell(@PathVariable(name = "spell") String spell) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(URL_SPELLS + spell);
            log.info("uri: {}", uri);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            return printService.printSpellDetails(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "errore";
    }

    @RequestMapping(value = "/spells", method = RequestMethod.GET)
    public String spellsList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(URL_SPELLS);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            SpellBook spellBook = spellBookService.setValuesByApiCall(response);

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            return objectMapper.writer(prettyPrinter).writeValueAsString(spellBook);

        } catch (IOException | URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

        return "errore";
    }

}
