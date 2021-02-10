package ru.chaichuk.hwapp.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import ru.chaichuk.hwapp.BuildConfig
import ru.chaichuk.hwapp.MainActivity
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie

class MovieNotificationImpl (private val context : Context) : MovieNotification {

    companion object {
        private const val CHANNEL_NEW_MOVIES = "new_movies"
        private const val REQUEST_CONTENT = 1
        private const val MOVIE_TAG = "movie"
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(
                    CHANNEL_NEW_MOVIES,
                    NotificationManagerCompat.IMPORTANCE_HIGH
                )
                    .setName(context.getString(R.string.channel_new_movies))
                    .setDescription(context.getString(R.string.channel_new_movies_description))
                    .build()
            )
        }
    }

    @WorkerThread
    override fun showNotification(movie: Movie) {
        //val icon = IconCompat.createWithContentUri(movie.poster)
        val contentUri = "${BuildConfig.BASE_URL}${movie.id}".toUri()
        var picture : Bitmap

        runBlocking {
            picture = loadBitmapFromUrl(movie.poster)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIES)
            .setContentTitle(movie.title)
            .setContentText(movie.overview)
            .setSmallIcon(R.drawable.movies_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(picture)
            )

        notificationManagerCompat.notify(MOVIE_TAG, movie.id, builder.build())
    }

    override fun dismissNotification(movieId : Int) {
        notificationManagerCompat.cancel(MOVIE_TAG, movieId)
    }

    private suspend fun loadBitmapFromUrl(url : String) : Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}