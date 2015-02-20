package com.mycompany.festivalhackmusic;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
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

/**
 * Created by Andon on 20/02/2015.
 */
public class SongSQLHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String SONG_TABLE_NAME = "Songs";
    private static final String DATABASE_NAME = "HeartBeat";

    private static final String TRACK_ID = "TrackID";
    private static final String TRACK_ID_TYPE = "VARCHAR(20)";

    private static final String ARTIST_ID = "ArtistID";
    private static final String ARTIST_ID_TYPE = "VARCHAR(20)";

    private static final String BPM = "BPM";
    private static final String BPM_TYPE = "INTEGER";

    private static final String SONG_TABLE_CREATE =
            "CREATE TABLE " + SONG_TABLE_NAME + " (" +
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
        ContentValues _Values = new ContentValues();
        //Get xml resource file
        Resources res = fContext.getResources();

    }



    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    private static String loadXML(String url){
        String xml = null;

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
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
