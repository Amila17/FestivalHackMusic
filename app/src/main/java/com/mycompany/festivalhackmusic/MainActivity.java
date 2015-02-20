package com.mycompany.festivalhackmusic;

import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.security.SignatureException;
import java.util.concurrent.ExecutionException;

import uk.co.sevendigital.android.sdk.api.SDIApi;
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

    public void onStream(View view) throws InterruptedException, ExecutionException, SignatureException, IOException {

        TextView txtView = (TextView) findViewById(R.id.onStreamOutput);
        txtView.setText("onStream Clicked");

        //SevenDigitalClient client = new SevenDigitalClient();
        //client.StreamMusic("38656894");
        SDIServerUtil.OauthConsumer sOauthConsumer = new SDIServerUtil.OauthConsumer("musichackday", "letmehack");
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        SDIApi sSDIApi = new SDIApi(this,requestQueue, sOauthConsumer);
        sSDIApi.streaming().getTrackPreview("38656894");

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
