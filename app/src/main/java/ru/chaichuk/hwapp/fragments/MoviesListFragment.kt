package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.MoviesListAdapter
import ru.chaichuk.hwapp.OnRecyclerItemClicked
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.loadMovies

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    var listener:OnMoviesListClickListener? = null
    private var rv_movies_list: RecyclerView? = null
    private var movies: List<Movie>? = null

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

        CoroutineScope(Dispatchers.Main).launch {
            movies = loadMovies(view.context)
            updateData()
        }
    }

    override fun onDestroyView() {
        rv_movies_list = null
        super.onDestroyView()
    }

    private fun updateData() {
        (rv_movies_list?.adapter as? MoviesListAdapter)?.apply {
            movies?.let {
                bindMovies(it)
            }
        }
    }


    private fun doOnClick(movie: Movie) {
        listener?.onMovieClick(movie)
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnClick(movie)
        }
    }

}