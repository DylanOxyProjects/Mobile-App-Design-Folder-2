package com.dylan.ridiculousrecipes;
/*      1. This activity contains a list of searching criteria. Each criteria is represented with a
        dropdown menu with all the options. (Hint: You need to use Spinner to create dropdown
        menu. https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list)
        2. You should generate these options for each criteria from the JSON file, instead of
        hardcoding them.
        3. You may add more criteria if you want to but the minimum requirement for this
        homework is to have “diet restriction”, “serving restriction”, and “preparation time”.
        4. User may choose 0 or more searching criteria. Display the result according to the
        combination of the options the user chose.
        5. There also should be a search button to bring up the result activity screen.
        6. Background color, font, and all design aspects are up to you.*/


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;
import android.content.Intent;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    public Spinner spinnerID1, spinnerID2, spinnerID3;
    public Toolbar toolbarID;
    public Button buttonID;
    public TextView textView1, textView2, textView3;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        spinnerID1 = findViewById(R.id.spinnerID1);
        spinnerID2 = findViewById(R.id.spinnerID2);
        spinnerID3 = findViewById(R.id.spinnerID3);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3  = findViewById(R.id.textView3 );

        toolbarID = findViewById(R.id.toolbarID2);
        toolbarID.setTitle("Search for a Recipe!");
        toolbarID.setBackgroundColor(getResources().getColor(R.color.maroon));
        toolbarID.setTitleTextColor(getResources().getColor(R.color.white));
        buttonID = findViewById(R.id.buttonID);
        buttonID.setTextSize(20);
        buttonID.setBackgroundColor(getResources().getColor(R.color.maroon));
        buttonID.setTextColor(getResources().getColor(R.color.white));




        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.dietRestriction, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerID1.setBackgroundColor(getResources().getColor(R.color.white));
        spinnerID1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.servingRestriction, android.R.layout.simple_spinner_item);
        //spinnerID2.setBackgroundColor(getResources().getColor(R.color.white));
        spinnerID2.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.prepTime, android.R.layout.simple_spinner_item);
        //spinnerID3.setBackgroundColor(getResources().getColor(R.color.white));
        spinnerID3.setAdapter(adapter3);


        buttonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call method to search for the recipes depending on what the user selected
                // the arguments will be the three selected values

                // arg0
                String dietRestriction = spinnerID1.getSelectedItem().toString();

                //arg1
                String servings = spinnerID2.getSelectedItem().toString();

                //arg2
                String cookTime = spinnerID3.getSelectedItem().toString();

                Intent recipeIntent = new Intent(SearchActivity.this, CustomRecipeList.class);
                recipeIntent.putExtra("dietRestriction", dietRestriction);
                recipeIntent.putExtra("servings", servings);
                recipeIntent.putExtra("cookTime", cookTime);

                startActivity(recipeIntent);





            }
        });
    }

    public ArrayList<Recipe> findMatchingRecipes(String dietRestriction, String servings, String cookTime) {
        // the goal of this method is to create a brand new RecipeList and delete Recipes that do not
        // match the arguments we are given
        // the remaining recipes will be returned as a RecipeList

        //create new recipeList
        ArrayList<Recipe> recipeList = Recipe.getRecipeFromFile("recipes.json", this);



        int servingsInt;
        // need to loop through RecipeList
        // instead of removing Recipe Items from the ArrayList while Im in the for loop
        // save index to arraylist and remove after so the for loop indexing doesn't break down
        int recipeListSize = recipeList.size();
        ArrayList<Integer> badRecipes = new ArrayList<>();
        for (int count = 0; count < recipeListSize; count++) {

            Recipe currentRecipe = recipeList.get(count);
            int currentTime = 0;


            // first check servings
            // servings needs to be converted to an integer if and only if it is not equal to "No Preference"
            if (!servings.equals("No Preference")){
                servingsInt = Integer.parseInt(servings);
                if (currentRecipe.servings !=servingsInt) {
                    badRecipes.add(count);
                    continue;
                }
            }

            //second check dietRestriction

            if (!dietRestriction.equals("No Preference")){
                if (!currentRecipe.dietLabel.equals(dietRestriction)) {
                    badRecipes.add(count);
                    continue;
                }
            }

            //finally check prep time

            if (!cookTime.equals("No Preference")){
                if (currentRecipe.prepTime.contains("1 hour".toLowerCase())) {
                    currentTime += 60;
                }

                if (currentRecipe.prepTime.contains("minutes".toLowerCase())) {
                    String currentPrepTimeString = currentRecipe.prepTime;
                    currentPrepTimeString = currentPrepTimeString.replace("minutes", "");

                    if (currentTime != 0)
                        currentPrepTimeString = currentPrepTimeString.replace("hour", "");

                    currentPrepTimeString = currentPrepTimeString.replace(" ", "");
                    int tempAdd = Integer.parseInt(currentPrepTimeString);
                    currentTime += tempAdd;
                }

                if (cookTime.equals("30 minutes or less")  && currentTime > 30){
                    badRecipes.add(count);
                }
                else if (cookTime.equals("1 hour or less") && currentTime > 60){
                    badRecipes.add(count);
                }
                else if(cookTime.equals("More than 1 hour") && currentTime < 60){
                    badRecipes.add(count);
                }
            }
    }


        for (int badSize = badRecipes.size(); badSize > 0; badSize--){
            int recipeToRemove = badRecipes.get(badSize-1);
            recipeList.remove(recipeToRemove);
        }
    return recipeList;
}
}
