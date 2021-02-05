package ru.chaichuk.hwapp.api_v3

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.chaichuk.hwapp.BuildConfig
import ru.chaichuk.hwapp.api_v3.dto.*
import ru.chaichuk.hwapp.utils.log

class MovieDbApi {

    suspend fun getPopularMovies() : List <MovieDTO> = withContext(Dispatchers.IO) {
        val moviesPage = RetrofitModule.moviesApi.popularMovies().log()
        return@withContext moviesPage.movies
    }

    /*suspend fun getMovieDetails(movieId : Int) : MovieDetailsDTO {
        return RetrofitModule.moviesApi.movieDetails(movieId)
    }*/

    /*suspend fun getMovieCredits(movieId : Int) : CreditsDTO {
        return RetrofitModule.moviesApi.movieCredits(movieId)
    }*/

    suspend fun getMovieDetailsWithCredits(movieId : Int) : MovieDetailsWithCreditsDTO = withContext(Dispatchers.IO) {
        return@withContext RetrofitModule.moviesApi.movieDetailsWithCredits(movieId).log()
    }

    private interface MoviesApi {
        @GET("movie/popular")
        suspend fun popularMovies(@Query("api_key") apiKey : String = BuildConfig.API_KEY) : MoviesListPageDTO

        /*
        @GET("movie/{movie_id}")
        suspend fun movieDetails(@Path("movie_id") movieId : Int, @Query("api_key") apiKey : String = BuildConfig.API_KEY) : MovieDetailsDTO

        @GET("movie/{movie_id}/credits")
        suspend fun movieCredits(@Path("movie_id") movieId : Int, @Query("api_key") apiKey : String = BuildConfig.API_KEY) : CreditsDTO
         */

        @GET("movie/{movie_id}")
        suspend fun movieDetailsWithCredits(@Path("movie_id") movieId : Int, @Query("api_key") apiKey : String = BuildConfig.API_KEY,
            @Query("append_to_response") append : String = "credits") : MovieDetailsWithCreditsDTO
    }

    private object RetrofitModule {
        private val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        private val json = Json {
            ignoreUnknownKeys = true
        }

        @Suppress("EXPERIMENTAL_API_USAGE")
        private val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)
    }

    /*companion object {
        private val TAG = MovieDbApi::class.java.simpleName
    }*/
}
