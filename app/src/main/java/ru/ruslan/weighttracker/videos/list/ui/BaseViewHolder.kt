package ru.ruslan.weighttracker.videos.list.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.ruslan.weighttracker.data.datasource.api.model.response.YoutubeModel
import ru.ruslan.weighttracker.videos.list.vm.model.VideoUI

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentPosition: Int = 0
    abstract fun clear()

    open fun onBind(position: Int, model: VideoUI?){
        currentPosition = position
        clear()
    }
}