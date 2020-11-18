package com.sahil.tiktok.home.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sahil.tiktok.R
import com.sahil.tiktok.model.VideoDataModel
import com.sahil.tiktok.utils.Constants
import com.varunest.sparkbutton.SparkEventListener
import kotlinx.android.synthetic.main.fragment_video_view.*


class VideoViewFragment: Fragment(R.layout.fragment_video_view) {

    private var player: SimpleExoPlayer? = null
    private var videoDataModel: VideoDataModel? = null
    private var storyUrl: String? = null
    private var dataSourceFactory: DataSource.Factory? = null
    private var played = true

    companion object {
        fun newInstance(videoDataModel: VideoDataModel) = VideoViewFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.DATA_STORE, videoDataModel)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoDataModel = arguments?.getParcelable(Constants.DATA_STORE)
        prepareVideoPlayer()
        setData()

        videoController.setOnClickListener {
            player?.playWhenReady = !played
            played= !played
        }

        image_view_option_like.setEventListener(object : SparkEventListener{
            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                if (buttonState) {
                    // Button is active
                    like_count.text = like_count.text.toString().toInt().plus(1).toString()
                } else {
                    // Button is inactive
                    like_count.text = like_count.text.toString().toInt().plus(-1).toString()
                }
            }

            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {}
        })

    }

    private fun prepareVideoPlayer(){
        val adaptiveTrackSelection: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())

        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(requireContext()),
            DefaultTrackSelector(adaptiveTrackSelection),
            DefaultLoadControl()
        )
        playerView?.useController = false
        playerView?.player = player

        val defaultBandwidthMeter = DefaultBandwidthMeter()
        dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "Exo2"), defaultBandwidthMeter
        )

    }

    private fun setData(){
        storyUrl = videoDataModel?.url

        fullName.text = videoDataModel?.title

        if (videoDataModel?.description?.length!! < 150){
            video_description.text = videoDataModel?.description
        }else{
            video_description.text = videoDataModel?.description?.substring(0,150).plus("...")
        }

        title.text = videoDataModel?.description

        storyUrl?.let { prepareExoPlayer(it) }
    }

    private fun prepareExoPlayer(linkUrl: String){
        val hls_url = linkUrl
        val mediaUri: Uri = Uri.parse(hls_url)
        val mediaSource: MediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            mediaUri
        )
        player?.prepare(mediaSource)
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    private fun releasePlayer() {
        player?.stop(true)
        player?.release()
    }

    override fun onPause() {
        pauseVideo()
        super.onPause()
    }

    private fun pauseVideo() {
        player?.playWhenReady = false
        player?.seekTo(0)
	}

    override fun onResume() {
        videoRestart()
        super.onResume()
    }

    private fun videoRestart() {
        if (player == null) {
            storyUrl?.let { prepareExoPlayer(it) }
        } else {
            player?.seekTo(0)
            player?.playWhenReady = true
        }
    }

}