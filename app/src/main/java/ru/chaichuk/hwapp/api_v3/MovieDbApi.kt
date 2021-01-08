package ru.chaichuk.hwapp.api_v3

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.api_v3.dto.CreditsDTO
import ru.chaichuk.hwapp.api_v3.dto.MovieDTO
import ru.chaichuk.hwapp.api_v3.dto.MovieDetailsDTO
import ru.chaichuk.hwapp.api_v3.dto.MoviesListPageDTO

class MovieDbApi {

    suspend fun getPopularMovies() : List <MovieDTO> {
        val moviesPage = RetrofitModule.moviesApi.popularMovies(HWApp.apiKey)
        return moviesPage.movies
    }

    suspend fun getMovieDetails(movie_id : Int) : MovieDetailsDTO {
        return RetrofitModule.moviesApi.movieDetails(movie_id, HWApp.apiKey)
    }

    suspend fun getMovieCredits(movie_id : Int) : CreditsDTO {
        return RetrofitModule.moviesApi.movieCredits(movie_id, HWApp.apiKey)
    }

    private interface MoviesApi {
        @GET("movie/popular")
        suspend fun popularMovies(@Query("api_key") apiKey : String) : MoviesListPageDTO

        @GET("movie/{movie_id}")
        suspend fun movieDetails(@Path("movie_id") movie_id : Int, @Query("api_key") apiKey : String) : MovieDetailsDTO

        @GET("movie/{movie_id}/credits")
        suspend fun movieCredits(@Path("movie_id") movie_id : Int, @Query("api_key") apiKey : String) : CreditsDTO
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
            .baseUrl(HWApp.baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)
    }

    companion object {
        private val TAG = MovieDbApi::class.java.simpleName
    }
}