package ru.chaichuk.hwapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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

    private var btnNewCalendarEvent: Button? = null
    private var isRationaleShown = false
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @SuppressLint("MissingPermission")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMoviesDetailsClickListener) {
            listener = context
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onCalendarPermissionGranted()
            } else {
                onCalendarPermissionNotGranted()
            }
        }
    }

    override fun onDetach() {
        listener = null
        requestPermissionLauncher.unregister()
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

        btnNewCalendarEvent = view.findViewById(R.id.btnAddMovieToCalendar)
        btnNewCalendarEvent?.setOnClickListener {
            onNewCalendarEvent()
        }

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


    private fun onNewCalendarEvent() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED -> onCalendarPermissionGranted()
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR) ->
                    showCalendarPermissionExplanationDialog()
                isRationaleShown -> showCalendarPermissionDeniedDialog()
                else -> requestCalendarPermission()
            }
        }
    }

    private fun requestCalendarPermission() {
        context?.let {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
        }
    }

    @RequiresPermission(Manifest.permission.WRITE_CALENDAR)
    private fun onCalendarPermissionGranted() {
        context?.let {
            Toast.makeText(context, "Есть права на запись в календарь", Toast.LENGTH_SHORT).show()
            TODO("add calendar event")
        }
    }

    private fun onCalendarPermissionNotGranted() {
        context?.let {
            Toast.makeText(context, "Нет прав на запись в календарь", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCalendarPermissionExplanationDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage("Нам очень нужен этот календарь")
                .setPositiveButton("Разрешить") { dialog, _ ->
                    isRationaleShown = true
                    requestCalendarPermission()
                    dialog.dismiss()
                }
                .setNegativeButton("Запретить") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun showCalendarPermissionDeniedDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage("Доступ к календарю запрещен. Разрешите его в настройках программы")
                .setPositiveButton("Перейти к настройкам") { dialog, _ ->
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + it.packageName)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Не разрешать") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

}
