package ru.chaichuk.hwapp.backgroundWorkers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.api_v3.MovieDbApi

class DbUpdateWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        try {
            Log.d("HWAppWorker", "<--- worker (${Thread.currentThread().name}) starting data receive from internet")
            val moviesFromNet = MovieDbApi().getMoviesFromNet()
            Log.d("HWAppWorker", "<--- worker (${Thread.currentThread().name}) saving data to database")
            HWApp.appMoviesDbRepository().saveAllMovies(moviesFromNet)
            Log.d("HWAppWorker", "<--- worker (${Thread.currentThread().name}) data saved to database")

            if(moviesFromNet.isNotEmpty()) {
                HWApp.appMovieNotification().showNotification(moviesFromNet.sortedByDescending { it.ratings }[0])
            }
        }
        catch (e : Exception) {
            Log.d("HWAppWorker", e.printStackTrace().toString())
            return Result.failure()
        }

        return Result.success()
    }

}
