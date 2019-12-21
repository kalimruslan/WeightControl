package ru.ruslan.weighttracker.ui.videos.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_video.view.*
import ru.ruslan.weighttracker.ui.OnItemClickListener
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.util.showToast
import ru.ruslan.weighttracker.ui.videos.list.vm.model.VideoUI

class VideosAdapter(
    private val context: Context?,
    private var data: MutableList<VideoUI>?,
    private val clickListener: OnItemClickListener?
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> VideosViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
            )
            VIEW_TYPE_LOADING -> VideoProgressHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
            else -> VideosViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val model = data?.get(position)
        holder.onBind(position, model)
    }

    override fun getItemViewType(position: Int): Int {
        return if(isLoaderVisible){
            if(position == data?.size?.minus(1) ?: position) VIEW_TYPE_LOADING
            else VIEW_TYPE_NORMAL
        } else{
            VIEW_TYPE_NORMAL
        }
    }

    fun addItems(newData: List<VideoUI>?) {
        newData?.let {
            data?.addAll(newData)
            notifyDataSetChanged()
        } ?: "Нет данных для отображения".showToast(context!!)
    }

    fun addLoading(){
        isLoaderVisible = true
        data?.add(VideoUI())
        notifyItemInserted(data?.size!! - 1)
    }

    fun removeLoading(){
        isLoaderVisible = false
        val position: Int = data?.size!! - 1
        val item: VideoUI? = data?.get(position)!!
        if(item != null){
            data?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear(){
        data?.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): VideoUI? =
        data?.get(position)

    inner class VideosViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun clear() {
        }

        private val item = itemView

        @SuppressLint("DefaultLocale", "SetTextI18n")
        override fun onBind(position: Int, model: VideoUI?) {
            super.onBind(position, model)
            item.tv_title.text = "${model?.title?.substring(0,1)?.toUpperCase()}${model?.title?.substring(1)}"
            item.tv_description.text = "${model?.description?.substring(0,1)?.toUpperCase()}${model?.description?.substring(1)}"
            val url = model?.url
            context?.let {
                Glide.with(it)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .fallback(R.drawable.img_placeholder)
                    .into(item.iv_thumbnails)
            }

            item.setOnClickListener{clickListener?.itemClick(position)}
        }
    }

    inner class VideoProgressHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun clear() {
        }

        private val item = itemView
    }
}