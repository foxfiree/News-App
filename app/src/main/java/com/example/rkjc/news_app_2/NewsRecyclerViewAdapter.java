package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {
    private static final String TAG = NewsRecyclerViewAdapter.class.getSimpleName();
    ArrayList<NewsItem> newsItems;
    Context context;

    //constructor for the adapter
    //recyclerView is created for the layout of this app because we have a bunch of news to load and we dont want to load it all at once
    //so it gets some views and "recycle" those views with old + new data as you scroll up and down. wow such resource and time saving
    public NewsRecyclerViewAdapter(ArrayList<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    //updates the viewHolders you see as you scroll around
    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    //before creating the viewHolder for the news items, gotta initialize whatever is needed
    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    //getting size of the news object//article array
    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    //viewHolder for what's going to display on the screen
    //TextView are linked to the ones created in the layout/news_item.xml files
    class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        //constructor for this NewsItemViewHolder inner class
        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }

        //changing the values/contents of the TextViews
        void bind(final int listIndex) {
            title.setText("Title: " + newsItems.get(listIndex).getTitle());
            description.setText("Description: " + newsItems.get(listIndex).getDescription());
            date.setText("Date: " + newsItems.get(listIndex).getPublishedAt());

            //whenever one of the news items is clicked, it directs you to the link of the full news
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urlString = newsItems.get(listIndex).getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    context.startActivity(intent);
                }
            });
        }
    }
}
