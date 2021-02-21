package ru.chaichuk.hwapp

import android.app.Application
import android.content.Context
import androidx.work.*
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import ru.chaichuk.hwapp.backgroundWorkers.DbUpdateWorker
import ru.chaichuk.hwapp.db.MoviesDbRepository
import ru.chaichuk.hwapp.notifications.MovieNotification
import ru.chaichuk.hwapp.notifications.MovieNotificationImpl
import java.util.concurrent.TimeUnit

class HWApp : Application(), ImageLoaderFactory {

    companion object {
        private lateinit var appContext: Context
        private lateinit var dbRepository: MoviesDbRepository
        private lateinit var movieNotification : MovieNotification

        fun appContext() : Context {
            return appContext
        }

        fun appMoviesDbRepository() : MoviesDbRepository {
            return dbRepository
        }

        fun appMovieNotification() : MovieNotification {
            return movieNotification
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        dbRepository = MoviesDbRepository(appContext)
        movieNotification = MovieNotificationImpl(appContext)

        movieNotification.initialize()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val dbUpdateWorker = PeriodicWorkRequestBuilder<DbUpdateWorker>(
            //8, TimeUnit.HOURS)
            15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
            "movieDbUpdate",
            ExistingPeriodicWorkPolicy.REPLACE,
            dbUpdateWorker)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}
