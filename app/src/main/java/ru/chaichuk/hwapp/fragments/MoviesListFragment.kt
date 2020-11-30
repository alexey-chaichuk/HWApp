package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import ru.chaichuk.hwapp.R

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    var listener:OnMoviesListClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMoviesListClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageViewListMovie1Bg = view.findViewById<ImageView>(R.id.imageViewListMovie1Bg)
        imageViewListMovie1Bg.setOnClickListener {
            listener?.onMovieClick()
        }
    }
}