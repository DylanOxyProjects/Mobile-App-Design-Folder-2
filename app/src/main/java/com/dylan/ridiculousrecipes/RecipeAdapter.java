package com.dylan.ridiculousrecipes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter{

    private Context contextID;
    private ArrayList<Recipe> recipeList;
    private LayoutInflater inflaterID;

    // Constructor
    public RecipeAdapter(Context contextID, ArrayList<Recipe> recipeList){
        //initialize instance variables
        this.contextID = contextID;
        this.recipeList = recipeList;
        this.inflaterID = (LayoutInflater)contextID.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        // check if view already exists, if it does u dnt need to inflate and findViewByID again

        if (view == null){
            // inflate!
/*          A LayoutInflater is one of the Android System Services that is responsible for taking your XML files that define a layout,
            and converting them into View objects. ...
            Android does most of the layout inflation for you when you call setContentView() in the onCreate() method of your activity.*/

            view = inflaterID.inflate(R.layout.recipe_items, viewGroup, false);
            // add views to folder
            holder = new ViewHolder();
            holder.titleView = view.findViewById(R.id.titleView);
            holder.imageView = view.findViewById(R.id.recipieView);
            holder.urlView = view.findViewById(R.id.urlView);
            holder.descriptionView = view.findViewById(R.id.descriptionID);
            holder.servingView = view.findViewById(R.id.servingsID);
            holder.preptimeView = view.findViewById(R.id.preptimeID);
            holder.dietlabelView = view.findViewById(R.id.dietlabelID);
            // add holder to view for future use
            view.setTag(holder);
        }
        else{
            //get the view holder
            holder = (ViewHolder)view.getTag();
        }

        // get relative subview of the row view
        TextView titleView = holder.titleView;
        ImageView imageView = holder.imageView;
        TextView urlView = holder.urlView;
        TextView descriptionView = holder.descriptionView;
        TextView servingView = holder.servingView;
        TextView preptimeView = holder.preptimeView;
        TextView dietlabelView = holder.dietlabelView;

        // get corresponding Recipe for each row
        Recipe recipe = (Recipe)getItem(position);

        // update the row view's textviews and imageview to display the information

        //update title
        titleView.setText(recipe.title);
        titleView.setTextSize(22);

        //update image
        Picasso.with(contextID).load(recipe.image).into(imageView);

        //update url
        //urlView.setText(recipe.url);

        //update description
        descriptionView.setText(recipe.description);
        descriptionView.setTextSize(12);

        //update servings
        String servingViewString = "Servings:  "  + Integer.toString(recipe.servings);
        servingView.setText(servingViewString);
        servingView.setTextSize(14);

        //update preptime
        String prepTimeString = "Prep Time:  " + recipe.prepTime;
        preptimeView.setText(prepTimeString);
        preptimeView.setTextSize(14);

        //update dietlabel
        String dietLabelString = "Diet Label:  " + recipe.dietLabel;
        dietlabelView.setText(dietLabelString);
        dietlabelView.setTextSize(14);



        return view;
    }



    public static class ViewHolder{

        TextView titleView;
        ImageView imageView;
        TextView urlView;
        TextView descriptionView;
        TextView servingView;
        TextView preptimeView;
        TextView dietlabelView;

    }
}

