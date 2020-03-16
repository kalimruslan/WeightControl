package ru.ruslan.weighttracker.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_weight.view.*
import ru.ruslan.weighttracker.R

class WeightAdapter(private val listener: WeightItemClickListener) : RecyclerView.Adapter<WeightAdapter.ViewHolder>() {

    private var list: MutableList<HomeUI> = mutableListOf()

    interface WeightItemClickListener{
        fun weightItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_weight, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeUI = list[position]
        holder.onBind(listener, position, homeUI)
    }

    fun setList(newList: List<HomeUI>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun getItemByPosition(position: Int): HomeUI = list[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val item = itemView

        @SuppressLint("SetTextI18n")
        fun onBind(listener: WeightItemClickListener, position: Int, homeUI: HomeUI){
            item.setOnClickListener { listener.weightItemClick(position) }

            item.tvDate.text = homeUI.photoDate
            item.tvWeight.text = "${homeUI.weightOnPhoto} кг."
            if(homeUI.photoId > 0) item.ivHasPhoto.visibility = View.VISIBLE
            else item.ivHasPhoto.visibility = View.GONE
        }

    }


}