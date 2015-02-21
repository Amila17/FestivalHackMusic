package com.mycompany.festivalhackmusic;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmilaPrabandhika on 21/02/2015.
 */
public class TracksClient
{

    public List<Track> GetTrackList() throws URISyntaxException, IOException {

        StringBuilder builder = new StringBuilder();
        URI uri = URIUtils.createURI("http", "tracksapi.apphb.com", -1,
                "api/tracks",null,null);
        HttpGet request = new HttpGet(uri);
        request.addHeader(new BasicHeader("Content-Type", "application/json"));

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        String jsonData = builder.toString();

        List<Track> tracks = new ArrayList<Track>();

        Gson jsonParser = new Gson();
        Type listType = new TypeToken<ArrayList<Track>>()
        {
        }.getType();
        tracks = jsonParser.fromJson(jsonData, listType);

        return tracks;
    }
}
