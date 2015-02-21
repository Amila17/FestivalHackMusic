package com.mycompany.festivalhackmusic;

import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by AmilaPrabandhika on 20/02/2015.
 */
public class SevenDigitalClient {

    public File GetTrack(String trackid) throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {
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
        qparams.add(new BasicNameValuePair("trackid", trackid));

        String base64Params = URLEncoder.encode(URLEncodedUtils.format(qparams, "UTF-8"));

        String signature = getSignature(pureUrl, base64Params);

        qparams.add(new BasicNameValuePair("oauth_signature", signature));

        URI uri = URIUtils.createURI("http", "stream.svc.7digital.net", -1,
                "/stream/catalogue",
                URLEncodedUtils.format(qparams, "UTF-8"), null);


        String url = uri.toString();

        HttpGet request = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);
        StatusLine status  = response.getStatusLine();

        File tempFile = File.createTempFile(trackid, ".mp3");

        if(status.getStatusCode() == 200) {
            HttpEntity entity = null;
            if (response.getEntity() != null) {
                entity = response.getEntity();
            }

            tempFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(tempFile);
            entity.writeTo(fos);
            entity.consumeContent();
            fos.flush();
            fos.close();
        }

        return tempFile;
    }

    private static String getSignature(String url, String params)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {


        String ENC = "UTF-8";
        StringBuilder base = new StringBuilder();
        base.append("GET&");
        base.append(url);
        base.append("&");
        base.append(params);
        System.out.println("Stirng for oauth_signature generation:" + base);

        byte[] keyBytes = ("letmehack" + "&").getBytes(ENC);

        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(
                ENC))), ENC).trim();
    }
}
