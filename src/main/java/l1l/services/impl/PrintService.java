package l1l.services.impl;

import l1l.models.Spell;
import l1l.services.interf.IPrintService;
import org.springframework.stereotype.Service;

@Service
public class PrintService implements IPrintService {

    @Override
    public String separateSpellDetails(Spell spell) {
            String str = "";
            String br = "</br>";
            String ss = "";

            str +=  spell.getSchool() + "</br>" +
                    spell.getCastTime() + "</br>" +
                    spell.getRange() + "</br>" +
                    spell.getComponents() + "</br>" +
                    spell.getDuration() + "</br>";
            str +=  spell.getDescription().replace(".", "</br>");

            return str;
        }

    @Override
    public String separateSpellDetailsStringBuilder(Spell spell, String spellName) {
        StringBuilder sb = new StringBuilder();
            sb.append("<head>");
                sb.append("<title>" + spellName + "</title>");
                sb.append("<link rel=\"stylesheet\" href=\"/css/bootstrap.css\">");
                sb.append("<link rel=\"stylesheet\" href=\"/css/search-bar.css\">");
            sb.append("</head>");
            sb.append("<body>");
                sb.append("<div>");
                    sb.append("<ul class=\"list-group\">");
                        sb.append("<li class=\"list-group-item\">" + spell.getOriginalName() + "</li>");
                        sb.append("<li class=\"list-group-item\">" + spell.getSchool() + "</li>");
                        sb.append("<li class=\"list-group-item\">" + spell.getCastTime() + "</li>");
                        sb.append("<li class=\"list-group-item\">" + spell.getRange() + "</li>");
                        sb.append("<li class=\"list-group-item\">" + spell.getComponents() + "</li>");
                        sb.append("<li class=\"list-group-item\">" + spell.getDuration() + "</li>");
                        sb.append("<li class=\"list-group-item\">" + spell.getDescription().replace(".", "</br>") + "</li>");
                    sb.append("</ul>");
                sb.append("</div>");
            sb.append("</body>");
        return sb.toString();
    }
}
