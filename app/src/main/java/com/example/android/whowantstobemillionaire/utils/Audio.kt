package com.example.android.whowantstobemillionaire.utils

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext

class Audio {
    var muteState = 100
    fun runAudio(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    fun pauseAudio(mediaPlayer: MediaPlayer) {
        mediaPlayer.pause()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun muteAudio(context: Context) {
        var mAudioManager = context.getSystemService(AudioManager::class.java)
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
        muteState = 0
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun unmuteAudio(context: Context) {
        var mAudioManager = context.getSystemService(AudioManager::class.java)
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0)
        muteState = 100
    }


}