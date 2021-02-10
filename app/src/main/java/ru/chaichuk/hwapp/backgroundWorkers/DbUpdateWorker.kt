package ru.chaichuk.hwapp.backgroundWorkers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.api_v3.MovieDbApi
import ru.chaichuk.hwapp.db.MoviesDbRepository
import java.lang.Exception

class DbUpdateWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        try {
            Log.d("HWAppWorker", "<--- worker (${Thread.currentThread().name}) starting data receive from internet")
            val moviesFromNet = MovieDbApi().getMoviesFromNet()
            Log.d("HWAppWorker", "<--- worker (${Thread.currentThread().name}) saving data to database")
            HWApp.appMoviesDbRepository().saveAllMovies(moviesFromNet)
            Log.d("HWAppWorker", "<--- worker (${Thread.currentThread().name}) data saved to database")
        }
        catch (e : Exception) {
            Log.d("HWAppWorker", e.printStackTrace().toString())
            return Result.failure()
        }

        return Result.success()
    }

}
