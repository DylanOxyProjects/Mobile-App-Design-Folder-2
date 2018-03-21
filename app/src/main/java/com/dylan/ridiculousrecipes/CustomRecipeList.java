package com.dylan.ridiculousrecipes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;
import java.util.ArrayList;

public class CustomRecipeList extends AppCompatActivity {

    public Toolbar toolbar;
    public ListView listView;
    static ArrayList<Recipe> recipeList;
    public Button button;
    NotificationCompat.Builder notification;
    private static final int UNIQUE_ID = 56856;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        toolbar = findViewById(R.id.toolbarID3);
        listView = findViewById(R.id.recipeListView);
        button = findViewById(R.id.urlButton);
        toolbar.setTitle("Here are all the Recipes that fit your search criteria!");
        toolbar.setBackgroundColor(getResources().getColor(R.color.maroon));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        notification  =  new NotificationCompat.Builder(this, "default");
        notification.setAutoCancel(true);

        Bundle bundle = getIntent().getExtras();
        String dietRestriction =  bundle.getString("dietRestriction");
        String servings =  bundle.getString("servings");
        String cookTime =  bundle.getString("cookTime");
        recipeList = findMatchingRecipes(dietRestriction, servings, cookTime);
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipeList);
        listView.setAdapter(recipeAdapter);
    }

    public void urlButtonClicked(View view) {
        int position = listView.getPositionForView(view);
        Recipe currentRecipe = recipeList.get(position);
        String currentURL = currentRecipe.url;
        String currentTitle = currentRecipe.title;

        //Build the notification
        notification.setSmallIcon(R.drawable.waffles);
        notification.setTicker("This is the ticker");
        notification.setContentTitle("The URL for " + currentTitle + " can be found here!");
        notification.setContentText(currentURL);

        Intent notificationIntent  = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(currentURL));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //builds the notification and issues it
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager. notify(UNIQUE_ID, notification.build());
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
                String currentPrepTimeString = currentRecipe.prepTime;
                if (currentPrepTimeString.contains("1 hour".toLowerCase())) {
                    currentTime += 60;
                    currentPrepTimeString = currentPrepTimeString.replace("1 hour", "");
                    currentPrepTimeString = currentPrepTimeString.replace("and", "");
                    currentPrepTimeString = currentPrepTimeString.replace(" ", "");
                }

                else if(currentPrepTimeString.contains(("hours".toLowerCase()))){
                    String charToRemove = currentPrepTimeString.charAt(0) + "";

                    currentPrepTimeString = currentPrepTimeString.replace("hours", "");
                    currentPrepTimeString = currentPrepTimeString.replace(charToRemove, "");
                    currentPrepTimeString = currentPrepTimeString.replace("and", "");
                    currentPrepTimeString = currentPrepTimeString.replace(" ", "");
                    int tempAdd = Integer.parseInt(charToRemove) * 60;
                    currentTime += tempAdd ;
                }

                if (currentPrepTimeString.contains("minutes".toLowerCase())) {
                    String charToRemove = currentPrepTimeString.charAt(0) + "";
                    int tempAdd = Integer.parseInt(charToRemove);
                    currentTime += tempAdd;
                }

                if (cookTime.equals("30 minutes or less")  && currentTime > 30){
                    badRecipes.add(count);
                }
                else if (cookTime.equals("1 hour or less") && currentTime > 60){
                    badRecipes.add(count);
                }
                else if(cookTime.equals("More than 1 hour") && currentTime <= 60){
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
