package com.mycompany.festivalhackmusic;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;


import java.io.IOException;
import java.util.ArrayList;

import java.security.SignatureException;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void sendNotification(View view) throws IOException {
        TwilioClient client = new TwilioClient();
        client.SendNotification("Eye of the tiger", 220);
    }

    public void getArtists(View view)  {
        GigSetListClient client = new GigSetListClient();
        ArrayList<String> artists = client.GetArtists();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStream(View view) throws InterruptedException, ExecutionException, SignatureException, IOException {

        TextView txtView = (TextView) findViewById(R.id.onStreamOutput);
        txtView.setText("onStream Clicked");
       // SevenDigitalService.getApi().streaming().getTrackPreview("38656894");

    }

}
