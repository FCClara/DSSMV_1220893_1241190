package com.example.projectdroid_1220893_1241190;

import android.content.Context;

public class ApiConfig {

    public static String getBaseUrl(Context c){
        String url = c.getResources().getText(R.string.BASE_URl).toString();
        System.out.println(url);
        return url;
    }

    public static String getApiKey(Context c){
    String api = c.getResources().getText(R.string.API_KEY).toString();
    System.out.println(api);
    return api;}
}
