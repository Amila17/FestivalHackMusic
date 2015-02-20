package com.mycompany.festivalhackmusic;

import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by AmilaPrabandhika on 20/02/2015.
 */
public class SevenDigitalClient {

    public void StreamMusic(String trackid)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/AC3b1ea47c6184a8870640f84b0ebd062c/Messages.json");


        try {
            Random rand = new Random();
            String url = "http://stream.svc.7digital.net/stream/catalogue?formatid=50&oauth_consumer_key=musichackday&oauth_signature_method=HMAC-SHA1&oauth_version=1.0&";

            int randomNum = rand.nextInt(99999999); String nonce = "3"+(Integer.toString(randomNum)); System.out.println(nonce);
            String oauthNonce = "oauth_nonce="+ Integer.toString(randomNum)+"&";
            String oauthTimeStamp = "oauth_timestamp="+System.currentTimeMillis()/1000+"&";
            String trackIdKey = "trackId="+ trackid+"&";
            String oauthSig = "oauth_signature=oaM4PUOncLtNKAoydWzChYfYwW4%3D'";

            String finalUrl = url + oauthNonce + oauthTimeStamp + trackIdKey + oauthSig;


            HttpGet httpGet = new HttpGet(finalUrl);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpGet);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    private String getB64Auth (String login, String pass) {
        String source=login+":"+pass;
        String ret=Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        return ret;
    }
}
