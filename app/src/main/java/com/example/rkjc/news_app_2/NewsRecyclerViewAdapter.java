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
    public ArrayList<NewsItem> newsItems;
    public Context context;

    public NewsRecyclerViewAdapter(ArrayList<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

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

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }

        void bind(final int listIndex) {
            title.setText(newsItems.get(listIndex).getTitle());
            description.setText(newsItems.get(listIndex).getDescription());
            date.setText(newsItems.get(listIndex).getPublishedAt());

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
