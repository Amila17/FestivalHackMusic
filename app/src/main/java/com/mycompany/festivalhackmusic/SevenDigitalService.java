package com.mycompany.festivalhackmusic;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

<<<<<<< HEAD
=======
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import uk.co.sevendigital.android.sdk.api.SDIApi;
import uk.co.sevendigital.android.sdk.util.SDIServerUtil;
>>>>>>> Added seven digital lib

/**
 * Created by AmilaPrabandhika on 20/02/2015.
 */
public class SevenDigitalService extends Application {

    private static RequestQueue sRequestQueue;
    private static SDIApi sSDIApi;
    private static SDIServerUtil.OauthConsumer sOauthConsumer;

    @Override public void onCreate() {
        super.onCreate();

		/* setup the volley request queue and image loader */
        sRequestQueue = Volley.newRequestQueue(this);
        int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8; // use 1/8th of the available memory for this memory cache

		/* setup the api, with our oauth consumer key and secret*/
        sOauthConsumer = new SDIServerUtil.OauthConsumer("musichackday", "letmehack");
        sSDIApi = new SDIApi(this, sRequestQueue, sOauthConsumer);
    }

    /**
     * Get the static Volley request queue
     * @return {@link com.android.volley.RequestQueue}
     */
    public static RequestQueue getRequestQueue() {
        return sRequestQueue;
    }

    /**
     * Get the Seven Digital Api
     * @return {@link uk.co.sevendigital.android.sdk.api.SDIApi}
     */
    public static SDIApi getApi() {
        return sSDIApi;
    }
}
