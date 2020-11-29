package ru.chaichuk.hwapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.chaichuk.hwapp.R

class MoviesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        val imageViewListMovie1Bg = view!!.findViewById<ImageView>(R.id.imageViewListMovie1Bg)
        imageViewListMovie1Bg.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, MoviesDetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}