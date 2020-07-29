package ru.ruslan.weighttracker.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_slider.view.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource

class SliderAdapter(private val listener: OnItemCLickListener): RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    private var data: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.tv_item.text = data[position]
        holder.item.setOnClickListener { v ->
            listener.clickItem(position)
        }
    }

    fun setData(data: List<String>){
        this.data = data
        notifyDataSetChanged()
    }

    fun getCurrentData(position: Int) = data[position]
    fun getPosition(item: String): Int = data.indexOf(item)

    interface OnItemCLickListener {
        fun clickItem(pos: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView
    }
}