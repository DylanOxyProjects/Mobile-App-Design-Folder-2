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

public class RecipeList extends AppCompatActivity {

    public Toolbar toolbar;
    public ListView listView;
    static ArrayList<Recipe> recipeList;
    public Button button;
    NotificationCompat.Builder notification;
    private static final int UNIQUE_ID = 46433;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        toolbar = findViewById(R.id.toolbarID3);
        toolbar.setTitle("Here are all the Recipes you can search for!");
        listView = findViewById(R.id.recipeListView);
        button = findViewById(R.id.urlButton);

        notification  =  new NotificationCompat.Builder(this, "default");
        notification.setAutoCancel(true);
        recipeList = Recipe.getRecipeFromFile("recipes.json", this);
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
}
