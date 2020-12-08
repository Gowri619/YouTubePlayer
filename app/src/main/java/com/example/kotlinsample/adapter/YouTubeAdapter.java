package com.example.kotlinsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kotlinsample.R;
import com.example.kotlinsample.model.VideosModel;
import com.example.kotlinsample.utils.TimesAgo;

import java.util.ArrayList;

public class YouTubeAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<VideosModel.Item> videoList;
    private ItemSelectedListener selectedListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public YouTubeAdapter(Context context, ArrayList<VideosModel.Item> videoList, ItemSelectedListener selectedListener) {
        this.context = context;
        this.videoList = videoList;
        this.selectedListener = selectedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
            return new ViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            VideosModel.Item item = videoList.get(position);
            holder.textView.setText(item.getSnippet().getTitle());
            holder.updatedDate.setText(TimesAgo.covertTimeToText(item.getSnippet().getPublishTime()));
            Glide.with(context).load(item.getSnippet().getThumbnails().getMedium().getUrl()).into(holder.imageView);
            holder.itemView.setOnClickListener(v -> selectedListener.onItemSelected(item));
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return videoList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, updatedDate;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textview);
            updatedDate = itemView.findViewById(R.id.updated_date);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface ItemSelectedListener {
        void onItemSelected(VideosModel.Item item);
    }
}
