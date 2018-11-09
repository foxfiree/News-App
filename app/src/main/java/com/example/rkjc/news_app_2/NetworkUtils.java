package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    //the full url:
    ////https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=5c072a322c2b42dabd38340690536b1c
    final static String BASE_URL = "https://newsapi.org/v1/articles";

    final static String PARAM_SOURCE = "source";
    final static String SOURCE = "the-next-web";

    final static String PARAM_SORT_BY = "sortBy";
    final static String SORT_BY = "latest";

    final static String PARAM_APIKEY = "apiKey";
    final static String APIKEY = "5c072a322c2b42dabd38340690536b1c";

    //constructing the full url based on param-value pairs created above
    public static URL buildURL() {
        URL url = null;
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, SOURCE)
                .appendQueryParameter(PARAM_SORT_BY, SORT_BY)
                .appendQueryParameter(PARAM_APIKEY, APIKEY)
                .build();

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //connecting to the url
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
