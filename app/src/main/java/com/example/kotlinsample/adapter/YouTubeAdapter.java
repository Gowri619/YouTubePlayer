package com.example.kotlinsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kotlinsample.R;
import com.example.kotlinsample.model.VideosModel;

import java.util.ArrayList;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideosModel.Item> videoList;
    private ItemSelectedListener selectedListener;

    public YouTubeAdapter(Context context, ArrayList<VideosModel.Item> videoList, ItemSelectedListener selectedListener) {
        this.context = context;
        this.videoList = videoList;
        this.selectedListener = selectedListener;
    }

    @NonNull
    @Override
    public YouTubeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubeAdapter.ViewHolder holder, int position) {
        VideosModel.Item item = videoList.get(position);
        holder.textView.setText(item.getSnippet().getTitle());
        holder.updatedDate.setText(item.getSnippet().getPublishTime());
        Glide.with(context).load(item.getSnippet().getThumbnails().getMedium().getUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> selectedListener.onItemSelected(item));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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

    public interface ItemSelectedListener {
        void onItemSelected(VideosModel.Item item);
    }
}
