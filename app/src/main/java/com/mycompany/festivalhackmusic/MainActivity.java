package com.mycompany.festivalhackmusic;

import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import com.twilio.sdk.TwilioRestException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


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

   //public void loadCSV(View view) throws IOException{
   //    SongSQLHelper helper = new SongSQLHelper(getApplicationContext());
   //    SQLiteDatabase db = helper.getWritableDatabase();
   //    boolean loaded = true;

   //    try{
   //        String externalStorage = Environment.getExternalStorageDirectory().toString();
   //        FileReader file = new FileReader("/emulated/0/emulated/0/Podcasts/test.csv");
   //        BufferedReader buffer = new BufferedReader(file);
   //        ContentValues contentValues=new ContentValues();
   //        String line = "";
   //        String tableName ="Songs";

   //        db.beginTransaction();
   //        while ((line = buffer.readLine()) != null) {
   //            String[] str = line.split("\t");


   //            contentValues.put("TrackID", str[0]);
   //            contentValues.put("ArtistID", str[1]);
   //            contentValues.put("BPM", str[2]);
   //            db.insert(tableName, null, contentValues);

   //        }
   //        db.setTransactionSuccessful();
   //        db.endTransaction();
   //    }catch (IOException e){

   //        TextView text = (TextView) findViewById(R.id.dbDisplay);
   //        File[] files = Environment.getExternalStorageDirectory().listFiles();
   //        String result = "";
   //        for(File f : files){
   //            result += f.toString();
   //        }

   //        text.setText(result);
   //        loaded = false;
   //    }

   //    if(loaded){
   //       TextView text = (TextView) findViewById(R.id.dbDisplay);
   //        Cursor curs = db.rawQuery("SELECT * FROM Songs;", new String[]{});
   //        String result = curs.getString(curs.getColumnIndex("TrackID"));
   //        result += "BLABLABLA";
   //        text.setText(result);
   //    }


   //}

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

    public void onStream(View view)
    {

    }

}
