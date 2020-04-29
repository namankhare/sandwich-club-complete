package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {



        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            Sandwich sandwich = new Sandwich();
            //new JSON object
            JSONObject sandwichJsonObject = new JSONObject(json);
            // JSON name
            JSONObject name = sandwichJsonObject.getJSONObject("name");
            //string name
            sandwich.setMainName(name.getString("mainName"));
            //also know as JSON
            JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
            if (alsoKnownAsJsonArray != null) {
                List<String> alsoKnownAsList = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsJsonArray.getString(i));
                }
                sandwich.setAlsoKnownAs(alsoKnownAsList);
            }

            //now parse the remaining fields which are flat, in string format and string array
            sandwich.setPlaceOfOrigin(sandwichJsonObject.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichJsonObject.getString("description"));
            sandwich.setImage(sandwichJsonObject.getString("image"));
            //ingredients field is a json array of type string
            JSONArray ingredientsJsonArray = sandwichJsonObject.getJSONArray("ingredients");
            if (ingredientsJsonArray != null) {
                List<String> ingredientsList = new ArrayList<>();
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredientsList.add(ingredientsJsonArray.getString(i));
                }
                sandwich.setIngredients(ingredientsList);
            }

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}