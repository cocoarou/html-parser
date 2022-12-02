package l1l.services.interf;

import l1l.models.SpellBook;
import org.springframework.http.ResponseEntity;

public interface ISpellBookService {
    SpellBook setValuesByApiCall(ResponseEntity<String> response);
}
