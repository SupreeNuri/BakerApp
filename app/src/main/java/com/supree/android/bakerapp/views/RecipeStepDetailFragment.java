package com.supree.android.bakerapp.views;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeStepDetailFragment extends Fragment {

    public static final String SELECTED_STEP = "selected_step";

    OnSkipPreviousOrNextListener mCallback;

    public interface OnSkipPreviousOrNextListener {
        void OnSkipPreviousOrNext(boolean isNext);
    }

    @BindView(R.id.vNoVideo)
    LinearLayout vNoVideo;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    SimpleExoPlayer mExoPlayer;

    @BindView(R.id.ivSkipPrevious)
    ImageView ivSkipPrevious;
    @BindView(R.id.ivSkipNext)
    ImageView ivSkipNext;

    private Step selectedStep;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnSkipPreviousOrNextListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSkipPreviousOrNextListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            selectedStep = getArguments().getParcelable(SELECTED_STEP);
        } else {
            //selectedStep = savedInstanceState.getParcelable(SELECTED_RECIPES);
        }

        if (selectedStep.getVideoURL() == null || selectedStep.getVideoURL().isEmpty()) {
            vNoVideo.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        } else {
            initializePlayer(Uri.parse(selectedStep.getVideoURL()));
        }

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "BakerApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @OnClick(R.id.ivSkipPrevious)
    public void skipPrevious() {
        mCallback.OnSkipPreviousOrNext(false);
    }

    @OnClick(R.id.ivSkipNext)
    public void skipNext() {
        mCallback.OnSkipPreviousOrNext(true);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }
}
