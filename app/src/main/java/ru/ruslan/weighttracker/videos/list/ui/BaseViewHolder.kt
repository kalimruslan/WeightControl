package ru.ruslan.weighttracker.videos.list.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.ruslan.weighttracker.videos.list.domain.model.YoutubeModel

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentPosition: Int = 0
    abstract fun clear()

    open fun onBind(position: Int, model: YoutubeModel?){
        currentPosition = position
        clear()
    }
}