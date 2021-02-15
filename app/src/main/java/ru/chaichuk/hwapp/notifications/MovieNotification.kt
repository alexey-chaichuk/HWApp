package ru.chaichuk.hwapp.notifications

import ru.chaichuk.hwapp.data.Movie

interface MovieNotification {
    fun initialize()
    fun showNotification(movie: Movie)
    fun dismissNotification(movieId: Int)
}