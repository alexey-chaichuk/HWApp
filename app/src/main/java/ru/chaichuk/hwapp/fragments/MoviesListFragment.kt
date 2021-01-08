package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import ru.chaichuk.hwapp.listAdapters.MoviesListAdapter
import ru.chaichuk.hwapp.listAdapters.OnRecyclerItemClicked
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.MoviesListLoader
import ru.chaichuk.hwapp.viewModels.MoviesListViewModel
import ru.chaichuk.hwapp.viewModels.MoviesListViewModelFactory

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by viewModels { MoviesListViewModelFactory() }

    var listener:OnMoviesListClickListener? = null
    private var rv_movies_list: RecyclerView? = null
    private var pb_loading_state: ProgressBar? = null

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
        pb_loading_state = view.findViewById(R.id.movies_list_loader)

        viewModel.loadMoviesList()
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateMoviesList)
        viewModel.loadingState.observe(this.viewLifecycleOwner, this::updateLoadingState)
    }

    override fun onDestroyView() {
        rv_movies_list = null
        super.onDestroyView()
    }

    private fun updateMoviesList(movies : List<Movie>) {
        (rv_movies_list?.adapter as? MoviesListAdapter)?.apply {
            bindMovies(movies)
        }
    }

    private fun updateLoadingState(loading : Boolean) {
        pb_loading_state?.isVisible = loading
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