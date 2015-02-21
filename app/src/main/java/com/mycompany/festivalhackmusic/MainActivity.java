package com.mycompany.festivalhackmusic;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.security.SignatureException;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    public static MediaPlayerService mediaPlayerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mediaPlayerService == null) {
            mediaPlayerService = new MediaPlayerService();
        }

        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SongSQLHelper myDbHelper = new SongSQLHelper(getApplicationContext());
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        boolean loaded = true;

    }

    public void sendNotification(View view) throws IOException {
        TwilioClient client = new TwilioClient();
        client.SendNotification("Eye of the tiger", 220);
    }


    public void getArtists(View view)  {
        GigSetListClient client = new GigSetListClient();
        ArrayList<String> artists = client.GetArtists();
    }


    public void onStream(View view) throws InterruptedException, ExecutionException, SignatureException, IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {

        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SevenDigitalClient sevenDigitalClient = new SevenDigitalClient();
                    File trackFile = sevenDigitalClient.GetTrack("38656894");

                    mediaPlayerService.PlayMusic(trackFile);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
         //   return true;
       // }

        return super.onOptionsItemSelected(item);
    }


}
