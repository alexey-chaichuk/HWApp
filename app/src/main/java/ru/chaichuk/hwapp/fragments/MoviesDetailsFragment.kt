package ru.chaichuk.hwapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.chaichuk.hwapp.R

class MoviesDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onStart() {
        super.onStart()
        val textViewBack = view!!.findViewById<TextView>(R.id.textViewBack)
        textViewBack.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, MoviesListFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}