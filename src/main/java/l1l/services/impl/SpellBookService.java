package l1l.services.impl;

import l1l.models.SpellBook;
import l1l.services.interf.ISpellBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpellBookService implements ISpellBookService {

    @Override
    public SpellBook setValuesByApiCall(ResponseEntity<String> response) throws JSONException {
        JSONObject json = new JSONObject(response.getBody());
        JSONArray results = (JSONArray) json.get("results");
        List<JSONObject> jsonObjects = populateJsonObjectList(results);

        return new SpellBook(jsonObjects.stream()
                .map(jsonObject -> {
                    try {
                        return (String) jsonObject.get("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList()));
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
