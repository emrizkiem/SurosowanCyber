package com.example.surosowancyber.implement.features.homedetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.CastItem
import com.example.surosowancyber.implement.data.network.ApiConfig

class CastAdapter() : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    private val listData: ArrayList<CastItem> = arrayListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCast: ImageView = itemView.findViewById(R.id.img_cast)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvCharacter: TextView = itemView.findViewById(R.id.tv_character)

        fun bindItems(item: CastItem) {
            tvName.text = item.name
            tvCharacter.text = item.character

            Glide.with(itemView.context)
                .load(ApiConfig.IMAGE_BASE_URL + item.profilePath)
                .into(imgCast)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listData[position])
    }

    fun setData(item: List<CastItem>) {
        this.listData.clear()
        this.listData.addAll(item)
        notifyDataSetChanged()
    }
}