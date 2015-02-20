package com.mycompany.festivalhackmusic;

import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.security.SignatureException;
import java.util.concurrent.ExecutionException;

import uk.co.sevendigital.android.sdk.api.SDIApi;
import uk.co.sevendigital.android.sdk.api.request.preview.SDIGetTrackPreviewRequest;
import uk.co.sevendigital.android.sdk.util.SDIServerUtil;
import uk.co.sevendigital.android.sdk.util.VolleyUtil;


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

    public void onStream(View view) throws InterruptedException, ExecutionException, SignatureException, IOException {



        //SevenDigitalClient client = new SevenDigitalClient();
        //client.StreamMusic("38656894");
        SDIServerUtil.OauthConsumer sOauthConsumer = new SDIServerUtil.OauthConsumer("musichackday", "letmehack");
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());


        SDIApi sSDIApi = new SDIApi(this.getApplicationContext(),requestQueue, sOauthConsumer);

        SDIGetTrackPreviewRequest.Result result =  sSDIApi.streaming().getTrackPreview("38656894");
        if(result.isSuccess())
        {
            File tempFile = File.createTempFile("38656894", ".mp3");
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(result.getTrackPreview());
            fos.close();
            FileInputStream fis = new FileInputStream(tempFile);

            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        else
        {
            TextView txtView = (TextView) findViewById(R.id.onStreamOutput);
            txtView.setText("onStream Failed");
        }

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


}
