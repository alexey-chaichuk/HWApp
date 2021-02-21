package ru.chaichuk.hwapp.listAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie


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
                    clickListener.onClick(movies[position], it)
                }
                holder.itemView.transitionName = movies[position].id.toString()
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
        poster.load(movie.poster) {
            crossfade(true)
            transformations(RoundedCornersTransformation(10f,10f,0f,0f))
            diskCachePolicy(CachePolicy.ENABLED)
        }
        if(movie.like) {
            like.setImageDrawable(
                ResourcesCompat.getDrawable(context.resources,
                    R.drawable.liked_icon, context.theme)
            )
        } else {
            like.setImageDrawable(
                ResourcesCompat.getDrawable(context.resources,
                    R.drawable.like_icon, context.theme)
            )
        }
        title.text = movie.title
        rating.rating = movie.ratings/2
        age.text = StringBuilder().append(movie.minimumAge.toString()).append("+").toString()
        duration.text = StringBuilder().append(movie.runtime.toString()).append(" MIN").toString()
        reviews.text = StringBuilder().append(movie.numberOfRatings).append(" REVIEWS").toString()
        genre.text = movie.genres.joinToString { it.name }
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

interface OnRecyclerItemClicked {
    fun onClick(movie: Movie, view : View)
}
