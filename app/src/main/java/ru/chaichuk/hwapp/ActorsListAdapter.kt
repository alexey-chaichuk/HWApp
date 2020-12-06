package ru.chaichuk.hwapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.chaichuk.hwapp.data.models.Actor

class ActorsListAdapter() : RecyclerView.Adapter<ActorsViewHolder>() {
    private var actors = listOf<Actor>()

    override fun getItemViewType(position: Int): Int {
        return when (actors.size) {
            0 -> VIEW_TYPE_EMPTY
            else -> VIEW_TYPE_ACTORS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return when (viewType) {
            VIEW_TYPE_EMPTY -> ActorsEmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_movie_empty, parent, false)
            )
            else -> ActorsDataViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.view_holder_actor, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
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

abstract class ActorsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

private class ActorsEmptyViewHolder(itemView: View) : ActorsViewHolder(itemView)
private class ActorsDataViewHolder(itemView: View) : ActorsViewHolder(itemView) {
    private val avatar: ImageView = itemView.findViewById(R.id.imageViewActorAvatar)
    private val name: TextView = itemView.findViewById(R.id.textViewActorName)

    fun onBind(actor: Actor) {

        context?.let {
            avatar.setImageDrawable(
                ResourcesCompat.getDrawable(
                    it.resources,
                    actor.avatar,
                    it.theme
                )
            )
        }

        name.text = actor.name
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

private const val VIEW_TYPE_EMPTY = 0
private const val VIEW_TYPE_ACTORS = 1