package ru.chaichuk.hwapp.listAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.chaichuk.hwapp.R
import ru.chaichuk.hwapp.data.Actor

class ActorsListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ActorsDataViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.view_holder_actor, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ActorsDataViewHolder -> {
                holder.onBind(actors[position])
            }
        }
    }

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActors : List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }
}

private class ActorsDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val avatar: ImageView = itemView.findViewById(R.id.imageViewActorAvatar)
    private val name: TextView = itemView.findViewById(R.id.textViewActorName)

    fun onBind(actor: Actor) {
        Log.d("HWApp", actor.picture)
        // next code crashes genymotion's emulator on linux (with or without crossfade)
        // but works on google's emulator without problems
        avatar.load(actor.picture) { crossfade(true) }
        name.text = actor.name
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context
