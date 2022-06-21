package l1l.services.interf;

import l1l.models.Spell;

public interface IPrintService {
    @Deprecated
    String separateSpellDetails(Spell spell);
    String separateSpellDetailsStringBuilder(Spell spell, String spellName);
}
