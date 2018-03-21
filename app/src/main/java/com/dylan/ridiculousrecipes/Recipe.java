package com.dylan.ridiculousrecipes;

import android.content.Context;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;


public class Recipe{
    /*
    1. This class should have all the fields for a Recipe.
    2. This class should use the same helper method used in lecture example to read from a JSON file.
    3. The class should have a static method that reads from recipes.json file and creates an ArrayList of Recipe and returns it.*/

    public String title;
    public String image;
    public String url;
    public String description;
    public int servings;
    public String prepTime;
    public String dietLabel;
    /*
    Need to implement static methods that read in json file and load into Recipe
    Static method that loads our recipe.json using the helper method
    this method will return an arrayList of recipes from the json file
*/

    public static ArrayList<Recipe> getRecipeFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

       //ead from JSON
       //se new TAGS
       //onstruct a Recipe object for each recipe in each JSON
        //add object to arrayList
        //return arraylist

        try {
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            for (int count = 0; count < recipes.length(); count++){
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(count).getString("title");
                recipe.image = recipes.getJSONObject(count).getString("image");
                recipe.url = recipes.getJSONObject(count).getString("url");
                recipe.description = recipes.getJSONObject(count).getString("description");
                recipe.servings = recipes.getJSONObject(count).getInt("servings");
                recipe.prepTime = recipes.getJSONObject(count).getString("prepTime");
                recipe.dietLabel = recipes.getJSONObject(count).getString("dietLabel");
                recipeList.add(recipe);
            }


        } catch (JSONException e){
            e.printStackTrace();
        }
        return recipeList;

    }


    private static String loadJsonFromAsset(String filename, Context context){
        String json = null;

        try{
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;

    }



}
