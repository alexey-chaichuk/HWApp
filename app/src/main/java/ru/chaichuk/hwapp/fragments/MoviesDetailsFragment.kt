package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.chaichuk.hwapp.ActorsListAdapter
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private var listener:OnMoviesDetailsClickListener? = null

    private var rv_actors: RecyclerView? = null
    private var iv_movie_backdrop: ImageView? = null
    private var tv_movie_title: TextView? = null
    private var rb_movie: RatingBar? = null
    private var tv_genre: TextView? = null
    private var tv_storyline: TextView? = null
    private var tv_reviews: TextView? = null
    private var tv_age: TextView? = null
    private var tv_cast: TextView? = null

    private var movie: Movie? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMoviesDetailsClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewBack = view.findViewById<TextView>(R.id.textViewBack)
        textViewBack.setOnClickListener {
            listener?.onDetailsBack()
        }
        rv_actors = view.findViewById(R.id.rv_actors)
        rv_actors?.adapter = ActorsListAdapter()

        iv_movie_backdrop = view.findViewById(R.id.imageViewMovieBackdrop)
        tv_movie_title = view.findViewById((R.id.textViewTitle))
        rb_movie = view.findViewById(R.id.ratingBarMovie)
        tv_genre = view.findViewById(R.id.textViewGenre)
        tv_storyline = view.findViewById(R.id.textViewStorylineText)
        tv_reviews = view.findViewById(R.id.textViewReviews)
        tv_age = view.findViewById(R.id.textViewAge)
        tv_cast = view.findViewById(R.id.textViewCastTitle)

        movie = arguments?.getParcelable<Movie>("movie")
        movie?.let {
            Glide.with(view.context).load(it.backdrop).into(iv_movie_backdrop)
            tv_movie_title?.text = it.title
            rb_movie?.rating = it.ratings/2
            tv_genre?.text = it.genres.joinToString { genre -> genre.name }
            tv_storyline?.text = it.overview
            tv_reviews?.text = StringBuilder().append(it.numberOfRatings).append(" REVIEWS").toString()
            tv_age?.text = StringBuilder().append(it.minimumAge.toString()).append("+").toString()
            if(it.actors.isEmpty()) {
                tv_cast?.visibility = View.GONE
                rv_actors?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        rv_actors = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        (rv_actors?.adapter as? ActorsListAdapter)?.apply {
            movie?.actors?.let { bindActors(it) }
        }
    }
}