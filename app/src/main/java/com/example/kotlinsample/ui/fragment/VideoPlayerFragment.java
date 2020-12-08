package com.example.kotlinsample.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kotlinsample.R;
import com.example.kotlinsample.model.VideosModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import butterknife.BindView;

public class VideoPlayerFragment extends BaseFragment {

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;
    @BindView(R.id.video_title1)
    TextView videoTitle1;
    @BindView(R.id.video_title)
    TextView videoTitle;

    public static String videoId = "";
    public static String title = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initYouTubePlayerView();
        videoTitle.setText(title);
        videoTitle1.setText(title);
    }

    public static VideoPlayerFragment newInstance(VideosModel.Item videoItem) {
        if (videoItem != null) {
            videoId = videoItem.getId().getVideoId();
            title = videoItem.getSnippet().getTitle();
        }
        return new VideoPlayerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_player;
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void dispose() {

    }

    private void initYouTubePlayerView() {
        // The player will automatically release itself when the activity is destroyed.
        // The player will automatically pause when the activity is stopped
        // If you don't add YouTubePlayerView as a lifecycle observer, you will have to release it manually.
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        videoId,
                        0f
                );
            }
        });
    }
}