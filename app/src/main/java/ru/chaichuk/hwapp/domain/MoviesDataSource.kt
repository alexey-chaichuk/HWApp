package ru.chaichuk.hwapp.domain

import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.models.Actor
import ru.chaichuk.hwapp.data.models.Movie

class MoviesDataSource {
    fun getActors(): List<Actor> {
        return listOf(
            Actor("Robert Downey Jr.", R.drawable.robert_downey_jr),
            Actor("Chris Evans", R.drawable.chris_evans),
            Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
            Actor("Chris Hemsworth", R.drawable.chris_hemsworth)
        )
    }

    fun getMovies(): List<Movie> {
        return listOf(
            Movie("Avengers: End Game", "some movie1", R.drawable.avengers_end_game, "13+", 4F, "137 MIN", false, "125 REVIEWS"),
            Movie("Tenet", "some movie2", R.drawable.tenet, "16+", 5F, "97 MIN", true, "98 REVIEWS"),
            Movie("Black Widow", "some movie3", R.drawable.black_widow, "13+", 4F, "102 MIN", false, "38 REVIEWS"),
            Movie("Wonder Woman 1984", "some movie3", R.drawable.wonder_woman, "13+", 5F, "120 MIN", false, "74 REVIEWS"),

            Movie("Tenet", "some movie3", R.drawable.tenet, "13+", 4F, "100 MIN", true, "98 REVIEWS"),
            Movie("Avengers: End Game", "some movie1", R.drawable.avengers_end_game, "13+", 4F, "137 MIN", false, "125 REVIEWS"),
            Movie("Tenet", "some movie2", R.drawable.tenet, "16+", 5F, "97 MIN", true, "98 REVIEWS"),
            Movie("Black Widow", "some movie3", R.drawable.black_widow, "13+", 4F, "102 MIN", false, "38 REVIEWS"),
            Movie("Wonder Woman 1984", "some movie3", R.drawable.wonder_woman, "13+", 5F, "120 MIN", false, "74 REVIEWS"),
            Movie("Tenet", "some movie3", R.drawable.tenet, "13+", 4F, "100 MIN", true, "98 REVIEWS"),
        )
    }
}