package ru.chaichuk.hwapp;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.chaichuk.hwapp.data.models.Movie


class MoviesListAdapter(private val clickListener: OnRecyclerItemClicked) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesDataViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.view_holder_movie, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesDataViewHolder -> {
                holder.onBind(movies[position])
                holder.itemView.setOnClickListener {
                    clickListener.onClick(movies[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

}

private class MoviesDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val poster: ImageView = itemView.findViewById(R.id.imageViewListMoviePoster)
    private val title: TextView = itemView.findViewById(R.id.textViewListMovieTitle)
    private val rating: RatingBar = itemView.findViewById(R.id.ratingBarListMovie)
    private val age: TextView = itemView.findViewById(R.id.textViewListAge)
    private val duration: TextView = itemView.findViewById(R.id.textViewListMovieDuration)
    private val reviews: TextView = itemView.findViewById(R.id.textViewListMovieReviews)
    private val like: ImageView = itemView.findViewById(R.id.imageViewLike)
    private val genre: TextView = itemView.findViewById(R.id.textViewListMovie1Genre)


    fun onBind(movie: Movie) {
        context?.let {
            poster.setImageDrawable(ResourcesCompat.getDrawable(it.resources,
                movie.poster,
                it.theme))

            if(movie.like) {
                like.setImageDrawable(ResourcesCompat.getDrawable(it.resources,
                    R.drawable.liked_icon,
                    it.theme))
            } else {
                like.setImageDrawable(ResourcesCompat.getDrawable(it.resources,
                    R.drawable.like_icon,
                    it.theme))
            }
        }
        title.text = movie.title
        rating.rating = movie.rating
        age.text = movie.age
        duration.text = movie.duration
        reviews.text = movie.reviews
        genre.text = movie.genre
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
}
