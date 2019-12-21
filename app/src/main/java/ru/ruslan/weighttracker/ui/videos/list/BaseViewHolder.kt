package ru.ruslan.weighttracker.ui.videos.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentPosition: Int = 0
    abstract fun clear()

    open fun onBind(position: Int, model: VideoUI?){
        currentPosition = position
        clear()
    }
}