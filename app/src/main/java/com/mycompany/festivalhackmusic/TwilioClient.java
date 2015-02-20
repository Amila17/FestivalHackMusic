package com.mycompany.festivalhackmusic;

import android.util.Base64;


import java.io.IOException;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



public class TwilioClient {

    public static final String ACCOUNT_SID = "AC3b1ea47c6184a8870640f84b0ebd062c";
    public static final String AUTH_TOKEN = "fdb3457f90c7a6f98622d444be289113";

    public void SendNotification(String songName, int heartRate) throws IOException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/AC3b1ea47c6184a8870640f84b0ebd062c/Messages.json");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("To", "07999791559"));
            nameValuePairs.add(new BasicNameValuePair("From", "+441513290087"));
            nameValuePairs.add(new BasicNameValuePair("Body", "I'm working out my heart rate is " + heartRate + " and I'm listening to " + songName));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httppost.setHeader("Authorization",getB64Auth(ACCOUNT_SID,AUTH_TOKEN));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    private String getB64Auth (String login, String pass) {
        String source=login+":"+pass;
        String ret="Basic "+ Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
        return ret;
    }
}