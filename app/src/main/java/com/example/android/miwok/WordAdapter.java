package com.example.android.miwok;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by toshiba on 7/25/2017.
 */

//Create a custom array adapter
public class WordAdapter extends ArrayAdapter<Word> {

    //define the backgroundColor variable
    private int backgroundColorId;

    //Define the constructor
    public WordAdapter(Context context, ArrayList<Word> words, int backgroundColorId){

        super(context, 0, words);
        this.backgroundColorId = backgroundColorId;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the data item for this position in the list
        Word currentWord = getItem(position);

        //Check if an existing view is being reused, otherwise inflate a new list item view
        //from the xml layout list_item
        View listItemView = convertView;
        if(listItemView == null){

            //Use a layout inflater to convert the list_item xml layout file to a View object
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }

        //Find the text container view,the 2 TextViews and the ImageView in the list_item.xml layout
        View textContainer = listItemView.findViewById(R.id.text_container);
        TextView miwokView = (TextView) listItemView.findViewById(R.id.miwok);
        TextView defaultView = (TextView) listItemView.findViewById(R.id.english);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);


        // Set the theme color for the list item

        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), backgroundColorId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);


        //Get the miwok translation from the current Word object and
        //set this text on the miwok TextView
        miwokView.setText(currentWord.getMiwokTranslation());

        //Get the default translation from the current Word object and
        //set this text on the default translation TextView
        defaultView.setText(currentWord.getDefaultTranslation());

        //Get the image resource ID from the current Word object and
        //set the image view of the list item
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }else{
            //hide the image view if there's no image
            imageView.setVisibility(View.GONE);
        }
        //Return the list item view (1 image view and 2 text views)
        return listItemView;
    }
}
