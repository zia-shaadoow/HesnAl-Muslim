package com.example.hesnalmuslimclone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.example.hesnalmuslimclone.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar


private const val TAG = "AudioPlayerBottomSheet"

class AudioPlayerBottomSheet(private val url: String) : BottomSheetDialogFragment() {
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var playButtonImageButton: ImageButton
    private lateinit var pauseButtonImageButton: ImageButton
    private lateinit var skipForwardImageButton: ImageButton
    private lateinit var skipBackwardImageButton: ImageButton
    private lateinit var myView: View
    private lateinit var linearLayout: LinearLayout
//    private lateinit var snackBar: Snackbar
    private var speed = 1.0F
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.audio_player_bottom_sheet_layout, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareMediaPlayer()
        initViews()
    }

    private fun initViews() {
        playButtonImageButton = myView.findViewById(R.id.playButtonImageButton)
        pauseButtonImageButton = myView.findViewById(R.id.pauseButtonImageButton)
        skipForwardImageButton = myView.findViewById(R.id.skipForwardImageButton)
        skipBackwardImageButton = myView.findViewById(R.id.skipBackwardImageButton)
        linearLayout = myView.findViewById(R.id.mediaControlsBottomSection)
//        snackBar = Snackbar.make(linearLayout, "من فضلك انتظر...", Snackbar.LENGTH_INDEFINITE)

        playButtonImageButton.setOnClickListener {
            if (exoPlayer.playbackState == Player.STATE_ENDED) {
                prepareMediaPlayer()
            }
            exoPlayer.play()
            playButtonImageButton.visibility = View.GONE
            pauseButtonImageButton.visibility = View.VISIBLE
        }

        pauseButtonImageButton.setOnClickListener {
//            simpleExoPlayer.playWhenReady = false
            exoPlayer.pause()
            playButtonImageButton.visibility = View.VISIBLE
            pauseButtonImageButton.visibility = View.GONE
        }

        skipForwardImageButton.setOnClickListener {
            if (speed == 3.0F) {
                speed = 1.0F
            } else {
                speed += .25F
            }
            val param = PlaybackParameters(speed)
            exoPlayer.setPlaybackParameters(param)
            Toast.makeText(requireContext(), "السرعة: $speed", Toast.LENGTH_SHORT).show()
        }

        skipBackwardImageButton.setOnClickListener {
            if (speed > .25F) {
                speed -= .25F
            }
            val param = PlaybackParameters(speed)
            exoPlayer.setPlaybackParameters(param)
            Toast.makeText(requireContext(), "السرعة: $speed", Toast.LENGTH_SHORT).show()
        }
    }


    private fun prepareMediaPlayer() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(requireContext(), Util.getUserAgent(requireContext(), "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(url))

        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        exoPlayer = SimpleExoPlayer.Builder(requireContext())
                .setMediaSourceFactory(mediaSourceFactory)
                .build()

        exoPlayer.addMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {
                        Log.i(TAG, "EXO : STATE_IDLE")
                    }
                    Player.STATE_BUFFERING -> {
//                        snackBar.show()
                        Log.i(TAG, "EXO : STATE_BUFFERING")
                    }
                    Player.STATE_READY -> {
//                        snackBar.dismiss()
                        Log.i(TAG, "EXO : STATE_READY")
                    }
                    Player.STATE_ENDED -> {
                        playButtonImageButton.visibility = View.VISIBLE
                        pauseButtonImageButton.visibility = View.GONE
//                        snackBar.dismiss()
                        Log.i(TAG, "EXO : STATE_ENDED")
                    }
                }
            }
        })
    }

    override fun onDestroy() {
//        simpleExoPlayer.playWhenReady = false
        exoPlayer.release()
        super.onDestroy()
    }





}