package com.mycompany.festivalhackmusic;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.sax.Element;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andon on 20/02/2015.
 */
public class SongSQLHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String SONG_TABLE_NAME = "Songs";
    private static final String DATABASE_NAME = "HeartBeat";
    private static final String NULL_COLUMN = "NullColumn";

    private static final String ID = "ID";
    private static final String ID_TYPE = "INTEGER";

    private static final String TRACK_ID = "TrackID";
    private static final String TRACK_ID_TYPE = "VARCHAR(20)";

    private static final String ARTIST_ID = "ArtistID";
    private static final String ARTIST_ID_TYPE = "VARCHAR(20)";

    private static final String BPM = "BPM";
    private static final String BPM_TYPE = "FLOAT";

    private static final String SONG_TABLE_CREATE =
            "CREATE TABLE " + SONG_TABLE_NAME + " (" + ID + " " + ID_TYPE + ", " +
                    TRACK_ID + " " +
                    TRACK_ID_TYPE + ", " + ARTIST_ID + " " +
                    ARTIST_ID_TYPE + ", " + BPM + " " +
                    BPM_TYPE + " );";


    private final Context fContext;


    SongSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        fContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SONG_TABLE_CREATE);
        //Add default records to animals
    }

    public void InsertData(SQLiteDatabase db)
    {
        ContentValues _Values = new ContentValues();
        //Get xml resource file
        TracksClient trackClient = new TracksClient();
        try {
            List<Track> tracks = trackClient.GetTrackList();


            for(Iterator<Track> i = tracks.iterator(); i.hasNext(); )
            {
                Track item = i.next();
                ContentValues values = new ContentValues();
                values.put(TRACK_ID, item.getSevenDigTrackId());
                values.put(ARTIST_ID, item.getSevenDigArtistId());
                values.put(BPM, item.getTempo());
                long newRowId;
                newRowId = db.insert(
                        SONG_TABLE_NAME,
                        NULL_COLUMN,
                        values);
            }
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> GetTrackId(String bpm)
    {
        String lowerRange = "65";
        String upperRange = "90";

        Integer intBpm = Integer.parseInt(bpm);
        if(intBpm > 65 && intBpm < 90)
        {
            lowerRange = "65";
            upperRange = "90";

        }
        if(intBpm > 91 && intBpm < 120)
        {
            lowerRange = "91";
            upperRange = "120";
        }
        if(intBpm > 121)
        {
            lowerRange = "121";
            upperRange = "300";
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT TrackID FROM Songs WHERE BPM BETWEEN '"+lowerRange+"' AND '"+upperRange + "'", null);

        ArrayList<Integer> trackIds = new ArrayList<Integer>();
        if (cursor.moveToFirst())
        {
            do
            {
                Integer data = cursor.getInt(cursor.getColumnIndex(TRACK_ID));
                trackIds.add(data);
            }
            while(cursor.moveToNext());
        }
        cursor.close();

        return trackIds;
    }


    //PSEUDO CODE, BECAUSE BACKUPS ARE NOT HARDCORE
    @Override
    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        switch(oldVersion) {

            case 2:
                //TODO implement
            case 3:
                //TODO implement
            case 4:
                //TODO implement
                break;
            default:
                throw new IllegalStateException("blabla");
        }
    }

}
