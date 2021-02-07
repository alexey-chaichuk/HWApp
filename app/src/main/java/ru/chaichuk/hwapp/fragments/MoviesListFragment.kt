package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.listAdapters.MoviesListAdapter
import ru.chaichuk.hwapp.listAdapters.OnRecyclerItemClicked
import ru.chaichuk.hwapp.viewModels.MoviesListViewModel
import ru.chaichuk.hwapp.viewModels.MoviesListViewModelFactory

@Suppress("unused")
class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by viewModels { MoviesListViewModelFactory() }

    private var listener:OnMoviesListClickListener? = null
    private var rvMoviesList: RecyclerView? = null
    private var pbLoadingState: ProgressBar? = null

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
        rvMoviesList = view.findViewById(R.id.rv_movies_list)
        rvMoviesList?.adapter = MoviesListAdapter(clickListener)
        pbLoadingState = view.findViewById(R.id.movies_list_loader)

        //viewModel.loadMoviesList()
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateMoviesList)
        viewModel.loadingState.observe(this.viewLifecycleOwner, this::updateLoadingState)
    }

    override fun onDestroyView() {
        rvMoviesList = null
        super.onDestroyView()
    }

    private fun updateMoviesList(movies : List<Movie>) {
        (rvMoviesList?.adapter as? MoviesListAdapter)?.apply {
            bindMovies(movies)
        }
    }

    private fun updateLoadingState(loading : Boolean) {
        pbLoadingState?.isVisible = loading
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