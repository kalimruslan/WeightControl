package ru.ruslan.weighttracker.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_weight.view.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.util.Constants

class WeightAdapter(private val listener: WeightItemClickListener) : RecyclerView.Adapter<WeightAdapter.ViewHolder>() {

    private var list: MutableList<HomeUI> = mutableListOf()
    private var sortDescDate = false
    private var sortDescWeight = false

    interface WeightItemClickListener{
        fun weightItemClick(position: Int)
        fun weightItemLongClicked(position: Int, item: View,
                                  homeUI: HomeUI)
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

    fun sort(type: String) {
        when(type){
            Constants.SORT_DATE -> {
                sortDescDate = if(sortDescDate) {
                    list.sortByDescending { it.photoDate }
                    false
                } else {
                    list.sortBy { it.photoDate }
                    true
                }
            }
            Constants.SORT_WEIGHT ->{
                sortDescWeight = if(sortDescWeight) {
                    list.sortByDescending { it.weightOnPhoto?.toDouble()  }
                    false
                } else {
                    list.sortBy { it.weightOnPhoto?.toDouble()  }
                    true
                }
            }
            Constants.SORT_PHOTO ->
                list.sortBy { it.photoId > 0 }
        }
        notifyDataSetChanged()
    }

    fun removeItemByPosition(adapterPosition: Int) {
        list.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val item = itemView

        @SuppressLint("SetTextI18n")
        fun onBind(listener: WeightItemClickListener, position: Int, homeUI: HomeUI){
            item.setOnClickListener { listener.weightItemClick(position) }
            item.setOnLongClickListener {
                listener.weightItemLongClicked(position, item, homeUI)
                true
            }

            item.tvDate.text = homeUI.photoDate
            item.tvWeight.text = "${homeUI.weightOnPhoto} кг."
            if(homeUI.photoId > 0) item.ivHasPhoto.visibility = View.VISIBLE
            else item.ivHasPhoto.visibility = View.INVISIBLE
        }

    }


}