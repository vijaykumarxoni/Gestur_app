package com.boochats.vksoni.customgesture;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
GestureLibrary library;
TextView tvResult;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult=(TextView)findViewById(R.id.txtResult);
        library=GestureLibraries.fromRawResource(this,R.raw.gestures);

        if(!library.load()){
         finish();}

        GestureOverlayView gestures=(GestureOverlayView)findViewById(R.id.gesture);

        gestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
                ArrayList<Prediction> predictionsArrayList=library.recognize(gesture);
                for (Prediction predictions:predictionsArrayList){

                    if (predictions.score>3.0){
                        tvResult.setText(predictions.name);

                        if(predictions.name.equalsIgnoreCase("c")){
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                       if(predictions.name.equalsIgnoreCase("alpha")){

                           Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                           startActivity(intentContact);
                       }



                    }
                }
            }
        });



    }
}
