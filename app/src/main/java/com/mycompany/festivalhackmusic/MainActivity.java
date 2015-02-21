package com.mycompany.festivalhackmusic;

import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
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
import org.apache.commons.codec.binary.Base64;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.security.SignatureException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import uk.co.sevendigital.android.sdk.api.SDIApi;
import uk.co.sevendigital.android.sdk.api.request.preview.SDIGetTrackPreviewRequest;
import uk.co.sevendigital.android.sdk.util.SDIServerUtil;
import uk.co.sevendigital.android.sdk.util.VolleyUtil;*/


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


    public void onStream(View view) throws InterruptedException, ExecutionException, SignatureException, IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {

        //OAuthConsumer consumer = new DefaultOAuthConsumer("musichackday", "letmehack");
/*        HttpGet request = new HttpGet("http://stream.svc.7digital.net/stream/catalogue");
        consumer.sign(request.)*/
        //http://stream.svc.7digital.net/stream/catalogue?
        // formatid=50&
        // oauth_consumer_key=musichackday&
        // oauth_nonce=862202325&
        // oauth_signature_method=HMAC-SHA1&
        // oauth_timestamp=1424471258&
        // oauth_version=1.0&
        // trackid=38656894&
        // oauth_signature=MdNXQ20TxUTFIe8MZr6DSUkhefE%3D
        Random rand = new Random();
        int randomNum = rand.nextInt(99999999); String nonce = "3"+(Integer.toString(randomNum)); System.out.println(nonce);
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        String pureUrl = URLEncoder.encode("http://stream.svc.7digital.net/stream/catalogue", "UTF-8");

        List<NameValuePair> qparams = new ArrayList<NameValuePair>();

        qparams.add(new BasicNameValuePair("formatid", "50"));
        // These params should ordered in key
        qparams.add(new BasicNameValuePair("oauth_consumer_key", "musichackday"));
        qparams.add(new BasicNameValuePair("oauth_nonce", ""
                + (int) (Math.random() * 100000000)));
        qparams.add(new BasicNameValuePair("oauth_signature_method",
                "HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", ""
                + (System.currentTimeMillis() / 1000)));
        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
        qparams.add(new BasicNameValuePair("trackid", "38656894"));

        String base64Params = URLEncoder.encode(URLEncodedUtils.format(qparams, "UTF-8"));

        String signature = getSignature(pureUrl, base64Params);

        qparams.add(new BasicNameValuePair("oauth_signature", signature));

        URI uri = URIUtils.createURI("http", "stream.svc.7digital.net", -1,
                "/stream/catalogue",
                URLEncodedUtils.format(qparams, "UTF-8"), null);


        String url = uri.toString();//"http://stream.svc.7digital.net/stream/catalogue?formatid=50&oauth_consumer_key=musichackday&oauth_nonce="+Integer.toString(randomNum)+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="+timeStamp+"&oauth_version=1.0&trackid=38656894&oauth_signature="+URLEncoder.encode(signature, "UTF-8");

        HttpGet request = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        StatusLine status  = response.getStatusLine();


        if(status.getStatusCode() == 200)
        {
            HttpEntity entity = null;
            if(response.getEntity() != null)
            {
                entity = response.getEntity();
            }
            File tempFile = File.createTempFile("38656894", ".mp3");
            tempFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempFile);
            entity.writeTo(fos);
            entity.consumeContent();
            fos.flush();
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

    private static String getSignature(String url, String params)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {
        /**
         * base has three parts, they are connected by "&": 1) protocol 2) URL
         * (need to be URLEncoded) 3) Parameter List (need to be URLEncoded).
         */

        String ENC = "UTF-8";
        StringBuilder base = new StringBuilder();
        base.append("GET&");
        base.append(url);
        base.append("&");
        base.append(params);
        System.out.println("Stirng for oauth_signature generation:" + base);
        // yea, don't ask me why, it is needed to append a "&" to the end of
        // secret key.
        byte[] keyBytes = ("letmehack" + "&").getBytes(ENC);

        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);
        Base64 base64 = new Base64();
        // encode it, base64 it, change it to string and return.
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(
                ENC))), ENC).trim();
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
