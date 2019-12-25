package ru.ruslan.weighttracker.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_slider.view.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource

class SliderAdapter: RecyclerView.Adapter<SliderItemViewHolder>() {

    private val data: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SliderItemViewHolder, position: Int) {
        holder.item.tv_item.text = data[position]
    }

    fun setData(data: ArrayList<String>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(view: View)
    }
}