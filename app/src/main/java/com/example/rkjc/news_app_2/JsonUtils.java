package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String JSONString){
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try{
            JSONObject object = new JSONObject(JSONString);
            JSONArray list = object.getJSONArray("articles");

            for(int i = 0; i < list.length(); i++){
                JSONObject newsItem = list.getJSONObject(i);
                String author = newsItem.getString("author");
                String title = newsItem.getString("title");
                String description = newsItem.getString("description");
                String url = newsItem.getString("url");
                String urlToImage = newsItem.getString("urlToImage");
                String publishedAt = newsItem.getString("publishedAt");

                newsList.add(new NewsItem(author, title, description, url, urlToImage, publishedAt));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }
}


