package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {


    public ColorsFragment() {
        // Required empty public constructor
    }

    //Define media player instance
    MediaPlayer mediaPlayer;

    //Define the activity's log tag
    private static final String LOG_TAG = ColorsActivity.class.getSimpleName();

    //Define the on completion listener to release resources upon playing
    //Called after the media player start method
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            releaseMediaPlayer();

            Log.v(LOG_TAG,"Done playing...resources released");
        }
    };


    //Define audio manager instance
    private AudioManager am;

    //Implement the OnAudioFocusChangeListener
    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ){

                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                //Pause playback and reset player to the start of the file.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

                //Toast.makeText(ColorsActivity.this,"AudioFocus is Lost Temporarily",Toast.LENGTH_SHORT).show();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){

                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
                //Toast.makeText(ColorsActivity.this,"AudioFocus is Gained",Toast.LENGTH_SHORT).show();
            }

            else if(focusChange == AUDIOFOCUS_LOSS){

                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
                //Toast.makeText(ColorsActivity.this,"AudioFocus is Lost ",Toast.LENGTH_SHORT).show();
            }

        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.word_list, container, false);


        //create and setup the AudioManager to request audio focus
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Create an ArrayList of Type Word to store words
        final ArrayList<Word> words = new ArrayList<Word>();

        //Create a list of words
        words.add(new Word("red", "weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black", "kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white", "kelelli",R.drawable.color_white,R.raw.color_white));

        //Define the array adapter to populate each item in the array list into the list view
        WordAdapter wordsAdapter = new WordAdapter(getActivity(), words,R.color.category_colors);

        //Bind the array adapter into the list view from the xml layout
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //get the audio resource id of the current word
                Word word = words.get(position);

                Log.v(LOG_TAG,"Current Word " + word);

                //clean up media player if it currently exists because a new sound file is to be played
                releaseMediaPlayer();


                // Request audio focus for playback
                int result = am.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request transient focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //prepare the audio file associated with the word object for playback
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                    // Start playback.
                    mediaPlayer.start();

                    //Toast.makeText(ColorsActivity.this,"AudioFocus request granted",Toast.LENGTH_SHORT).show();


                    //Set up a OnCompletionListener to release resources once playing audio is complete
                    //Also unregister the AudioFocusChangeListener
                    mediaPlayer.setOnCompletionListener(onCompletionListener);

                }



            }
        });

        return rootView;

    }




    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            am.abandonAudioFocus(afChangeListener);
        }


    }


    @Override
    public void onStop() {
        super.onStop();

        //Release the media player resources once the application is stopped
        releaseMediaPlayer();
    }
}
