package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.chaichuk.hwapp.ActorsListAdapter
import ru.chaichuk.hwapp.MoviesListAdapter
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.domain.MoviesDataSource

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private var listener:OnMoviesDetailsClickListener? = null
    private var rv_actors: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMoviesDetailsClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        rv_actors = null
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
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    private fun updateData() {
        (rv_actors?.adapter as? ActorsListAdapter)?.apply {
            bindActors(MoviesDataSource().getActors())
        }
    }
}