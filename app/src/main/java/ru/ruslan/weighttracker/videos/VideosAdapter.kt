package ru.ruslan.weighttracker.videos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_video.view.*
import ru.ruslan.weighttracker.OnItemClickListener
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.model.YoutubeModel
import ru.ruslan.weighttracker.util.showToast

class VideosAdapter(private val context: Context?,
                    private var data: List<YoutubeModel>?,
                    private val clickListener: OnItemClickListener?): RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return VideosViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size?:0

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val model = data?.get(position)
        holder.bind(position, model)
    }

    fun addItems(newData: List<YoutubeModel>?){
        newData?.let {
            data = emptyList()
            data = newData
            notifyDataSetChanged()
        }?: "Нет данных для отображения".showToast(context!!)
    }

    inner class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val item = itemView

        fun bind(position: Int, model: YoutubeModel?){
            item.tv_title.text = model?.snippet?.title
            item.tv_description.text = model?.snippet?.description
            val url = model?.snippet?.thumbnails?.default?.url
            context?.let {
                Glide.with(it).load(url).into(item.iv_thumbnails)
            }
        }

    }
}