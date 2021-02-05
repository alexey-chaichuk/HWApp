package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import ru.chaichuk.hwapp.listAdapters.ActorsListAdapter
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.viewModels.MovieDetailsViewModel
import ru.chaichuk.hwapp.viewModels.MovieDetailsViewModelFactory

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModelFactory() }

    private var listener: OnMoviesDetailsClickListener? = null

    private var rvActors: RecyclerView? = null
    private var ivMovieBackdrop: ImageView? = null
    private var tvMovieTitle: TextView? = null
    private var rbMovie: RatingBar? = null
    private var tvGenre: TextView? = null
    private var tvStoryline: TextView? = null
    private var tvReviews: TextView? = null
    private var tvAge: TextView? = null
    private var tvCast: TextView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMoviesDetailsClickListener) {
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
        rvActors = view.findViewById(R.id.rv_actors)
        rvActors?.adapter = ActorsListAdapter()

        ivMovieBackdrop = view.findViewById(R.id.imageViewMovieBackdrop)
        tvMovieTitle = view.findViewById((R.id.textViewTitle))
        rbMovie = view.findViewById(R.id.ratingBarMovie)
        tvGenre = view.findViewById(R.id.textViewGenre)
        tvStoryline = view.findViewById(R.id.textViewStorylineText)
        tvReviews = view.findViewById(R.id.textViewReviews)
        tvAge = view.findViewById(R.id.textViewAge)
        tvCast = view.findViewById(R.id.textViewCastTitle)

        viewModel.movie.observe(this.viewLifecycleOwner, this::updateMovie)
        arguments?.getParcelable<Movie>("movie")?.let { viewModel.loadMovie(it) }
    }

    override fun onDestroyView() {
        rvActors = null
        super.onDestroyView()
    }

    private fun updateMovie(movie: Movie) {
        Log.d("HWApp", movie.backdrop)
        ivMovieBackdrop?.load(movie.backdrop) {
            crossfade(true)
            diskCachePolicy(CachePolicy.ENABLED)
        }
        tvMovieTitle?.text = movie.title
        rbMovie?.rating = movie.ratings / 2
        tvGenre?.text = movie.genres.joinToString { genre -> genre.name }
        tvStoryline?.text = movie.overview
        tvReviews?.text =
            StringBuilder().append(movie.numberOfRatings).append(" REVIEWS").toString()
        tvAge?.text = StringBuilder().append(movie.minimumAge.toString()).append("+").toString()
        if (movie.actors.isEmpty()) {
            tvCast?.visibility = View.GONE
            rvActors?.visibility = View.GONE
        } else {
            (rvActors?.adapter as? ActorsListAdapter)?.apply {
                bindActors(movie.actors)
            }
        }
    }
}