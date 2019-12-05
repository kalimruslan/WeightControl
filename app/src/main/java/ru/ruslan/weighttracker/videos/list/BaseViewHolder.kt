package ru.ruslan.weighttracker.videos.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.ruslan.weighttracker.model.YoutubeModel

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentPosition: Int = 0
    abstract fun clear()

    open fun onBind(position: Int, model: YoutubeModel?){
        currentPosition = position
        clear()
    }
}