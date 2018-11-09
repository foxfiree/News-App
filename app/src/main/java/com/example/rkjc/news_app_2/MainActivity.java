package com.example.rkjc.news_app_2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter adapter;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();

    //before starting up the app, we gotta do these stuff (like get the recycler view and the news items)
    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recyclerview);

        /*
           --ignore this, it has been cleaned up on the bottom--

           LinearLayoutManager layoutManager = new LinearLayoutManager(this);
           newsList.setLayoutManager(layoutManager);
           newsList.setHasFixedSize(true);
        */

        adapter = new NewsRecyclerViewAdapter(newsItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //with the help of asynctask, things are running and updating in the background while u look at the app/news
    //things like getting the information for new items as u scroll before the data actually gets loaded into the app/UI in the postexecute
    public class newsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchURL = params[0];
            String newsResults = null;

            try {
                newsResults = NetworkUtils.getResponseFromHttpUrl(searchURL);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return newsResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {
            super.onPostExecute(newsSearchResults);
            newsItems = JsonUtils.parseNews(newsSearchResults);
            adapter.newsItems.addAll(newsItems);
            adapter.notifyDataSetChanged();
        }
    }

    //make the options menu (holds the title and that refresh button in this case)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //when something in the options menu is clicked (in this case, the refresh button), it goes back to ur api code and reloads the app
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClickedId = item.getItemId();
        if (itemClickedId == R.id.get_news) {
            makeNewsSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeNewsSearchQuery() {
        URL newsSearchUrl = NetworkUtils.buildURL();
        new newsQueryTask().execute(newsSearchUrl);
    }

}
