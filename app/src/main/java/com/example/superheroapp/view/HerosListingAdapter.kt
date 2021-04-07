package com.example.superheroapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.superheroapp.R
import com.example.superheroapp.model.HerosResponse

class HerosListingAdapter(private var list : List<HerosResponse>,private val empty : (Boolean) -> Unit) : RecyclerView.Adapter<HerosListingAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = list[position].name
        holder.strength.text = list[position].powerStats.strength.toString()
        Glide.with(holder.itemView.context)
                .load(list[position].image.url)
                .into(holder.image)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image: ImageView = itemView.findViewById(R.id.coverImage)
        val name: TextView = itemView.findViewById(R.id.HeroName)
        val strength: TextView = itemView.findViewById(R.id.Strength)

    }

    fun filterSearch(searchText : String? , dataList : List<HerosResponse>) {
        if(searchText.isNullOrEmpty()){
            this.list = dataList
            notifyDataSetChanged()
        }else {
            val newList = mutableListOf<HerosResponse>()
            dataList.forEach {
                if (it.name.contains(searchText, true)) {
                    newList.add(it)
                }
            }
            this.list = newList
            notifyDataSetChanged()
        }
        empty.invoke(list.isEmpty())
    }
}