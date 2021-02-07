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
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.utils.log
import kotlin.random.Random

class MovieDbApi {

    suspend fun getMoviesFromNet() : List<Movie> = withContext(Dispatchers.IO) {

        //return@withContext getPopularMovies()
        return@withContext getPopularMovies(page = Random.nextInt(100))
            .map { movieDTO ->

                val movieDetailsWithCreditsDTO =
                    getMovieDetailsWithCredits((movieDTO.id.toInt()))
                val genres: List<Genre> =
                    movieDetailsWithCreditsDTO.genres.map { Genre(it.id.toInt(), it.name) }
                val actors: List<Actor> = movieDetailsWithCreditsDTO.credits.cast.mapNotNull {
                    it.profilePath?.let { picture ->
                        Actor(
                            it.id.toInt(),
                            it.name,
                            BuildConfig.BASE_IMAGE_W200_URL + picture
                        )
                    }
                }

                Movie(
                    movieDTO.id.toInt(),
                    movieDTO.title,
                    movieDTO.overview,
                    BuildConfig.BASE_IMAGE_W780_URL + movieDTO.posterPath,
                    BuildConfig.BASE_IMAGE_W780_URL + movieDTO.backdropPath,
                    movieDTO.voteAverage.toFloat(),
                    movieDTO.voteCount.toInt(),
                    if (movieDTO.adult) 16 else 13,
                    movieDetailsWithCreditsDTO.runtime.toInt(),
                    genres,
                    actors,
                    true
                )
            }
    }

    private suspend fun getPopularMovies(page : Int) : List <MovieDTO> = withContext(Dispatchers.IO) {
        val moviesPage = RetrofitModule.moviesApi.popularMovies(page = page).log("getPopularMovies api item")
        return@withContext moviesPage.movies
    }

    private suspend fun getMovieDetailsWithCredits(movieId : Int) : MovieDetailsWithCreditsDTO = withContext(Dispatchers.IO) {
        return@withContext RetrofitModule.moviesApi.movieDetailsWithCredits(movieId).log("getMovieDetailsWithCredits api item")
    }

    private interface MoviesApi {
        @GET("movie/popular")
        suspend fun popularMovies(@Query("api_key") apiKey : String = BuildConfig.API_KEY,
                                  @Query("page") page : Int = 1) : MoviesListPageDTO

        @GET("movie/{movie_id}")
        suspend fun movieDetailsWithCredits(@Path("movie_id") movieId : Int, @Query("api_key") apiKey : String = BuildConfig.API_KEY,
            @Query("append_to_response") append : String = "credits") : MovieDetailsWithCreditsDTO
    }

    private object RetrofitModule {
        private val client = OkHttpClient().newBuilder()
            //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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

}
