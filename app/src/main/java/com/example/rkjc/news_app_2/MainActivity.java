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

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recyclerview);

      //  LinearLayoutManager layoutManager = new LinearLayoutManager(this);
     //   newsList.setLayoutManager(layoutManager);
     //   newsList.setHasFixedSize(true);

        adapter = new NewsRecyclerViewAdapter(newsItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

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
            /*
            if (newsSearchResults != null && !newsSearchResults.equals("")) {
                searchResultsTextView.setText(newsSearchResults);
            }
            */
            super.onPostExecute(newsSearchResults);
            newsItems = JsonUtils.parseNews(newsSearchResults);
            adapter.newsItems.addAll(newsItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


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
