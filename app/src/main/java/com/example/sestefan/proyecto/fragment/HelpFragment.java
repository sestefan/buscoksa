package com.example.sestefan.proyecto.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.example.sestefan.proyecto.R;

public class HelpFragment extends Fragment {

    VideoView videoView;
    ToggleButton toggleButton;

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        videoView = v.findViewById(R.id.videoview);
        toggleButton = v.findViewById(R.id.toggleButton);
        toggleButton.setVisibility(View.INVISIBLE);

        String path = "android.resource://" + getContext().getPackageName() + "/" + R.raw.help_video;
        videoView.setVideoURI(Uri.parse(path));

        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
