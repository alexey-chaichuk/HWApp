package ru.chaichuk.hwapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.chaichuk.hwapp.R

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    var listener:OnMoviesDetailsClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMoviesDetailsClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewBack = view.findViewById<TextView>(R.id.textViewBack)
        textViewBack.setOnClickListener {
            listener?.onDetailsBack()
        }
    }
}