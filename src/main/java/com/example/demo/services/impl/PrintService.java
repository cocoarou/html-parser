package com.example.demo.services;

import com.example.demo.models.Spell;
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
}
