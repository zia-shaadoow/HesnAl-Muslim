package com.example.hesnalmuslimclone.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hesnalmuslimclone.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

public class AudioPLayerBottomSheetJava extends BottomSheetDialogFragment {
    private String audioUrl;
    private View view;
    private MediaPlayer mediaPlayer;
    public AudioPLayerBottomSheetJava(String audioUrl) {
        this.audioUrl = audioUrl;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.audio_player_bottom_sheet_layout, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playAudio(audioUrl);
    }

    private void playAudio(String url) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(MediaPlayer::start);

    }
}
