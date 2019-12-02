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

class VideosAdapter(private val context: Context?,
                    private val data: List<YoutubeModel>?,
                    private val clickListener: OnItemClickListener?): RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideosViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size?:0

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val model = data?.get(position)
        holder.bind(position, model)
    }

    inner class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView

        fun bind(position: Int, model: YoutubeModel?){
            item.tv_title.text = data?.get(position)?.snippet?.title
            item.tv_description.text = data?.get(position)?.snippet?.description
            val url = data?.get(position)?.snippet?.thumbnails?.url
            context?.let {
                Glide.with(it).load(url).into(item.iv_thumbnails)
            }
        }

    }
}