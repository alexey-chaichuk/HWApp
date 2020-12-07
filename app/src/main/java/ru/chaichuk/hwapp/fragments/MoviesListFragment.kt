package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.chaichuk.hwapp.MoviesListAdapter
import ru.chaichuk.hwapp.OnRecyclerItemClicked
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.models.Movie
import ru.chaichuk.hwapp.domain.MoviesDataSource

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    var listener:OnMoviesListClickListener? = null
    private var rv_movies_list: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMoviesListClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movies_list = view.findViewById(R.id.rv_movies_list)
        rv_movies_list?.adapter = MoviesListAdapter(clickListener)
    }

    override fun onDestroyView() {
        rv_movies_list = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        (rv_movies_list?.adapter as? MoviesListAdapter)?.apply {
            bindMovies(MoviesDataSource().getMovies())
        }
    }


    private fun doOnClick(movie: Movie) {
        rv_movies_list?.let { rv ->
            Snackbar.make(
                rv,
                movie.title,
                Snackbar.LENGTH_SHORT)
                .show()
        }
        listener?.onMovieClick()
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }

}