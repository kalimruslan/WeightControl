package ru.ruslan.weighttracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ruslan.weighttracker.model.YoutubeModel

class VideosAdapter(private val data: List<YoutubeModel>): RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideosViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}