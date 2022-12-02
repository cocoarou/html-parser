package l1l.services.impl;

import l1l.models.SpellBook;
import l1l.services.interf.ISpellBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
@Slf4j
public class SpellBookService implements ISpellBookService {

    @Override
    public SpellBook setValuesByApiCall(ResponseEntity<String> response) {
        log.info("response: {}", response.toString());

        return null;
    }

}
