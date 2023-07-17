package l1l.services.impl;

import com.google.gson.Gson;
import l1l.models.Spell;
import l1l.services.interf.ISpellService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class SpellService implements ISpellService {

    @Deprecated
    public Spell setValuesById(Document doc, String id) {
        Spell spell = new Spell();
        StringBuilder str = new StringBuilder();

        String content = Objects.requireNonNull(doc.getElementById(id)).outerHtml();
        Document document = Jsoup.parse(content);

        Elements p = document.select("p");
        Elements ul = document.select("ul");

        spell.setOriginalName(p.get(0).text());
        spell.setSchool(p.get(1).text());

        if(ul.size() > 1) {
            // old layout
            spell.setCastTime(ul.get(0).text());
            spell.setRange(ul.get(1).text());
            spell.setComponents(ul.get(2).text());
            spell.setDuration(ul.get(3).text());
        } else {
            // new layout
            String stringToParse = ul.get(0).text();
            String castTime =   stringToParse.substring(stringToParse.indexOf("Tempo di lancio"), stringToParse.indexOf("Gittata"));
            String range =      stringToParse.substring(stringToParse.indexOf("Gittata"), stringToParse.indexOf("Componenti"));
            String components = stringToParse.substring(stringToParse.indexOf("Componenti"), stringToParse.indexOf("Durata"));
            String duration =   stringToParse.substring(stringToParse.indexOf("Durata"));
            spell.setCastTime(castTime);
            spell.setRange(range);
            spell.setComponents(components);
            spell.setDuration(duration);
        }

        for(int i = 2; i < p.size(); i++) {
            str.append(p.get(i).text());
        }

        spell.setDescription(str.toString());

        return spell;
    }

    @Override
    public Spell returnSpellValues(ResponseEntity<String> response) throws JSONException {
        JSONObject json = new JSONObject(response.getBody());

        JSONObject schoolJson = (JSONObject) json.get("school");
        String school = (String) schoolJson.get("name");

        String castTime = (String) json.get("casting_time");

        String range = (String) json.get("range");

        JSONArray componentsArray = (JSONArray) json.get("components");
        String components_ = "";
        try {
            for (int i = 0; i < componentsArray.length(); i++) {
                components_ += componentsArray.getString(i);
                components_ += ", ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String components = components_.substring(0, components_.length() - 2);

        String duration = (String) json.get("duration");

        JSONArray descriptionArray = (JSONArray) json.get("desc");
        String description = "";
        try {
            for (int i = 0; i < descriptionArray.length(); i++) {
                description += descriptionArray.getString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray higherLevelArray = (JSONArray) json.get("higher_level");
        String higherLevel = "";
        try {
            for (int i = 0; i < higherLevelArray.length(); i++) {
                higherLevel += higherLevelArray.getString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String originalName = (String) json.get("name");

        Boolean concentration = (Boolean) json.get("concentration");

        Boolean ritual = (Boolean) json.get("ritual");

        String material = "";
        try {
            material = (String) json.get("material");
        } catch(JSONException e) {
            log.warn("The spell has no material property");
        }

        Integer level = (Integer) json.get("level");

        JSONObject damage = null;
        try {
            damage = (JSONObject) json.get("damage");
        } catch (JSONException e) {
            log.warn("The spell has no damage property");
        }

        JSONObject damageTypeJson = (damage != null) ? (JSONObject) damage.get("damage_type") : null;
        String damageType = (damageTypeJson != null) ? (String) damageTypeJson.get("name") : "";

        Gson gson = new Gson();
        Map<String, String> damageAtSlotLevel;
        try {
            damageAtSlotLevel = (damage != null) ? gson.fromJson(damage.get("damage_at_slot_level").toString(), Map.class) : new HashMap<>();
        } catch (JSONException e) {
            log.warn("The spell has no damage_at_slot_level property");
            damageAtSlotLevel = new HashMap<>();
        }

        return new Spell(school, castTime, range, components, duration, description, higherLevel, originalName, concentration, ritual, material, level, damageType, damageAtSlotLevel);
    }
}
