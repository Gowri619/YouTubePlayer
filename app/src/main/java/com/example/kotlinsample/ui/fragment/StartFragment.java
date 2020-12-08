package com.example.kotlinsample.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kotlinsample.R;
import com.example.kotlinsample.adapter.YouTubeAdapter;
import com.example.kotlinsample.model.VideosModel;
import com.example.kotlinsample.utils.Animations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;

public class StartFragment extends BaseFragment implements YouTubeAdapter.ItemSelectedListener {

    private static final String FRAGMENT_NAME = "Start";
    String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCJUP4L8ZMYGnpI4uc81Zn4Q&order=date&maxResults=25&key=AIzaSyA7j6oj3hIYZEe7kY1MRm8JyNpqYTwbGvQ";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private YouTubeAdapter youTubeAdapter;
    private ArrayList<VideosModel.Item> videoList = new ArrayList<>();
    private boolean isLoading;
    private String nextPageToken = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(manager);
        Animations.showAnimatedProgress(requireActivity(), null);
        videoList.clear();
        String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCJUP4L8ZMYGnpI4uc81Zn4Q&order=date&maxResults=25&key=AIzaSyA7j6oj3hIYZEe7kY1MRm8JyNpqYTwbGvQ";
        getVideosList(URL);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == videoList.size() - 1
                            && nextPageToken != null && !nextPageToken.isEmpty()) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    public void setTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_start;
    }

    @Override
    public void dispose() {
    }

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    private void getVideosList(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            Animations.hideAnimatedProgress();
            GsonBuilder builder = new GsonBuilder();
            Gson mGson = builder.create();
            VideosModel videosModel = mGson.fromJson(response, VideosModel.class);
            nextPageToken = videosModel.getNextPageToken();
            if (isLoading) {
                videoList.remove(videoList.size() - 1);
                int scrollPosition = videoList.size();
                youTubeAdapter.notifyItemRemoved(scrollPosition);
            }
            for (int i = 0; i < videosModel.getItems().size(); i++) {
                if (videosModel.getItems().get(i).getId().getKind().equals("youtube#video") && videosModel.getItems().get(i).getId().getVideoId() != null) {
                    videoList.add(videosModel.getItems().get(i));
                }
            }
            if (!isLoading) {
                youTubeAdapter = new YouTubeAdapter(requireContext(), videoList, this);
                recyclerView.setAdapter(youTubeAdapter);
            } else {
                youTubeAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, error -> {
            Animations.hideAnimatedProgress();
            error.printStackTrace();
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadMore() {
        videoList.add(null);
        recyclerView.post(() -> youTubeAdapter.notifyItemInserted(videoList.size() - 1));
        String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&pageToken=" + nextPageToken + "&channelId=UCJUP4L8ZMYGnpI4uc81Zn4Q&order=date&maxResults=25&key=AIzaSyA7j6oj3hIYZEe7kY1MRm8JyNpqYTwbGvQ";
        getVideosList(URL);
    }

    @Override
    public void onItemSelected(VideosModel.Item item) {
        if (fragmenChannel != null) {
            fragmenChannel.showVideo(item);
        }
    }
}
