package com.example.hesnalmuslimclone.exoplayer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hesnalmuslimclone.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class RadioPlayerActivity : AppCompatActivity() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var startButton : Button
    private lateinit var pauseButton : Button
    private lateinit var url : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        startButton = findViewById(R.id.btnStart)
        pauseButton = findViewById(R.id.btnStop)

        url = intent.getStringExtra("url").toString()

        prepareMediaPlayer()
        initListeners()
    }

    private fun prepareMediaPlayer() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(url))

        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = SimpleExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()

        simpleExoPlayer.addMediaSource(mediaSource)
        simpleExoPlayer.prepare()
    }

    private fun initListeners() {
        startButton.setOnClickListener {
            simpleExoPlayer.playWhenReady = true
        }

        pauseButton.setOnClickListener {
            simpleExoPlayer.playWhenReady = false
        }
    }

    override fun onDestroy() {
        simpleExoPlayer.playWhenReady = false
        super.onDestroy()
    }

}
