package l1l.services.impl;

import com.google.gson.Gson;
import l1l.models.Spell;
import l1l.services.interf.IPrintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PrintService implements IPrintService {

    @Override
    public String separateSpellDetails(Spell spell) {
        String str = "";

        str += spell.getSchool() + "</br>" +
                spell.getCastTime() + "</br>" +
                spell.getRange() + "</br>" +
                spell.getComponents() + "</br>" +
                spell.getDuration() + "</br>";
        str += spell.getDescription().replace(".", "</br>");

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

    @Override
    public String printSpellDetails(ResponseEntity<String> response) throws JSONException, IOException {
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

        String material = (json.get("material") != null) ? (String) json.get("material") : "";

        Integer level = (Integer) json.get("level");
        JSONObject damage = null;
        try {
            damage = (JSONObject) json.get("damage");
        } catch (JSONException e) {
            log.error("The spell has no damage property");
        }

        JSONObject damageTypeJson = (damage != null) ? (JSONObject) damage.get("damage_type") : null;
        String damageType = (damageTypeJson != null) ? (String) damageTypeJson.get("name") : "";

        Gson gson = new Gson();
        Map<String, String> damageAtSlotLevel = (damage != null) ? gson.fromJson(damage.get("damage_at_slot_level").toString(), Map.class) : new HashMap<>();

        Spell spell = new Spell(school, castTime, range, components, duration, description, higherLevel, originalName, concentration, ritual, material, level, damageType, damageAtSlotLevel);

        StringBuilder sb = new StringBuilder();
        sb.append("<head>");
        sb.append("<title>" + spell.getOriginalName() + "</title>");
        sb.append("<link rel=\"stylesheet\" href=\"/css/bootstrap.css\">");
        sb.append("<link rel=\"stylesheet\" href=\"/css/search-bar.css\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<div>");
        sb.append("<ul class=\"list-group\">");
        sb.append("<li class=\"list-group-item\"> Name: " + spell.getOriginalName() + "</li>");
        sb.append("<li class=\"list-group-item\"> School: " + spell.getSchool() + "</li>");
        sb.append("<li class=\"list-group-item\"> Level: " + spell.getLevel() + "</li>");
        sb.append("<li class=\"list-group-item\">Cast Time: " + spell.getCastTime() + "</li>");
        sb.append("<li class=\"list-group-item\">Range: " + spell.getRange() + "</li>");
        sb.append("<li class=\"list-group-item\">Components: " + spell.getComponents() + "</li>");
        sb.append("<li class=\"list-group-item\">Material: " + spell.getMaterial() + "</li>");
        sb.append("<li class=\"list-group-item\">Duration: " + spell.getDuration() + "</li>");
        sb.append("<li class=\"list-group-item\">Ritual: " + spell.getRitual() + "</li>");
        sb.append("<li class=\"list-group-item\">Concentration: " + spell.getConcentration() + "</li>");
        sb.append("<li class=\"list-group-item\">Damage Type: " + spell.getDamageType() + "</li>");
        sb.append("<li class=\"list-group-item\">Description: " + spell.getDescription().replace(".", "</br>") + "</li>");
        sb.append("<li class=\"list-group-item\">At Higher Levels: " + spell.getHigherLevel().replace(".", "</br>") + "</li>");
        sb.append("<li class=\"list-group-item\">Damage at Slot Level: " + spell.getDamageAtSlotLevel() + "</li>");
        sb.append("</ul>");
        sb.append("</div>");
        sb.append("</body>");

        return sb.toString();
    }

    private List<JSONObject> populateJsonObjectList(JSONArray array) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                jsonObjects.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObjects;
    }
}
