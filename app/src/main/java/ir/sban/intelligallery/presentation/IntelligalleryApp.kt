package ir.sban.intelligallery.presentation

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IntelligalleryApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        Log.d("MyApplication", "newImageLoader called, configuring Coil!")
        return ImageLoader.Builder(this)
            .components {
                // Add the video frame decoder factory
                add(VideoFrameDecoder.Factory())
            }
            // Optional: Enable detailed logging for debugging
            .logger(DebugLogger(Log.VERBOSE))
            // Optional: Configure other things like disk cache, memory cache, etc.
            .build()
    }
}