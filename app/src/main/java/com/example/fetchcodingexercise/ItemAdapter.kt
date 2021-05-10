package com.example.fetchcodingexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*

/*
    Simple adapter class to place data in each row of the RecyclerView
 */
class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    var mItems : List<ItemModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val id = view.id_tv
        val listId = view.list_id_tv
        val name = view.name_tv


        fun bind(data: ItemModel){
            id.text = data.id.toString()
            listId.text = data.listId.toString()
            name.text = data.name
        }
    }

}