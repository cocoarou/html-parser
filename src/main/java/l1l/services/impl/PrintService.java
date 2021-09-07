package l1l.services.impl;

import l1l.models.Spell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class PrintService {

    private static String log(String msg, String... vals) {
        return String.format(msg, vals);
    }

    public String print(Document doc, String cssQuery) {
        String str = doc.title() + "\n";

        Elements elements = doc.select(cssQuery);

        for(Element element : elements) {
            str += log("%s\n\t%s\n", element.attr("title"), element.absUrl("href"));
        }
        return str;
    }

    public String printSpellsById(Document doc, String id) {
        String str = doc.title() + "\n";

        String content = doc.getElementById(id).outerHtml();
        Document document = Jsoup.parse(content);

        Elements p = document.select("p");
        Elements ul = document.select("ul");

        str +=  p.get(0).text() + "\n";
        str += ul.get(0).text() + "\n";
        str += ul.get(1).text() + "\n";
        str += ul.get(2).text() + "\n";
        str += ul.get(3).text() + "\n";
        str +=  p.get(1).text() + "\n";
        str +=  p.get(2).text() + "\n";

        return str;

    }

    public String cleanJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }

    @Deprecated
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

    public String separateSpellDetailsStringBuilder(Spell spell, String spellName) {
        StringBuilder sb = new StringBuilder();
            sb.append("<head>");
                sb.append("<title>" + spellName + "</title>");
                sb.append("<link rel=\"stylesheet\" href=\"/css/bootstrap.css\">");
                sb.append("<link rel=\"stylesheet\" href=\"/css/search-bar.css\">");
            sb.append("</head>");
            sb.append("<body>");
                sb.append("<div>");
                    sb.append("<ul>");
                        sb.append("<li>" + spell.getSchool() + "</li>");
                        sb.append("<li>" + spell.getCastTime() + "</li>");
                        sb.append("<li>" + spell.getRange() + "</li>");
                        sb.append("<li>" + spell.getComponents() + "</li>");
                        sb.append("<li>" + spell.getDuration() + "</li>");
                        sb.append("<li>" + spell.getDescription().replace(".", "</br>") + "</li>");
                    sb.append("</ul>");
                sb.append("</div>");
            sb.append("</body>");
        return sb.toString();
    }
}
