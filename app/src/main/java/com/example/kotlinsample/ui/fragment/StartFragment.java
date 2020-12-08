package com.example.kotlinsample.ui.fragment;

import android.os.Bundle;
import android.view.View;

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

import butterknife.BindView;

public class StartFragment extends BaseFragment implements YouTubeAdapter.ItemSelectedListener {

    private static final String FRAGMENT_NAME = "Start";
    public String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCJUP4L8ZMYGnpI4uc81Zn4Q&order=date&maxResults=25&key=AIzaSyA7j6oj3hIYZEe7kY1MRm8JyNpqYTwbGvQ";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    YouTubeAdapter youTubeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(manager);
        getVideosList();
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

    private void getVideosList() {
        Animations.showAnimatedProgress(requireActivity(), null);
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            Animations.hideAnimatedProgress();
            GsonBuilder builder = new GsonBuilder();
            Gson mGson = builder.create();
            VideosModel videosModel = mGson.fromJson(response, VideosModel.class);
            youTubeAdapter = new YouTubeAdapter(requireContext(), videosModel.getItems(), this);
            recyclerView.setAdapter(youTubeAdapter);
        }, error -> {
            Animations.hideAnimatedProgress();
            error.printStackTrace();
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(VideosModel.Item item) {
        if (fragmenChannel != null) {
            fragmenChannel.showVideo(item);
        }
    }
}
