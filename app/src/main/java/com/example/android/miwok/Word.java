package com.example.android.miwok;

/**
 * Created by toshiba on 7/25/2017.
 */

//Custom class to create objects which contains the miwok and default translation words
//Class to represent data within each list item
public class Word {

    //Define the miwok translation word
    private String miwokTranslation;

    //Define the default translation word
    private String defaultTranslation;

    //Define the image resource ID
    private int imageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    //Define the audio resource ID
    private int audioResourceId;

    //Define a public constructor
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){

        //Assign parameters to the class fields
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.audioResourceId = audioResourceId;

    }

    //Define another public constructor
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId){

        //Assign parameters to the class fields
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;


    }

    //Define accessor methods

    //get the miwok word translation
    public String getMiwokTranslation(){

        return miwokTranslation;
    }

    @Override
    public String toString() {
        return "Word{" +
                "miwokTranslation='" + miwokTranslation + '\'' +
                ", defaultTranslation='" + defaultTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }

    //get the default translation
    public String getDefaultTranslation(){

        return defaultTranslation;
    }

    //get the image resource ID
    public int getImageResourceId(){

        return imageResourceId;
    }

    //Returns whether the object has an image or not
    public boolean hasImage(){

        return imageResourceId != NO_IMAGE_PROVIDED;
    }

    //get the audio resource ID
    public int getAudioResourceId(){

        return audioResourceId;
    }


}
