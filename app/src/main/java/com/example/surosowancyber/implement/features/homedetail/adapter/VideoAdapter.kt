package com.example.surosowancyber.implement.features.homedetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.VideosItem
import com.example.surosowancyber.implement.data.network.ApiConfig

class VideoAdapter() : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private val listData: ArrayList<VideosItem> = arrayListOf()
    var onItemClick: ((VideosItem) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cv_video)
        private val imgThumbnail: ImageView = itemView.findViewById(R.id.img_video)

        fun bindItems(item: VideosItem) {
            cardView.setOnClickListener {
                onItemClick?.invoke(item)
            }

            Glide.with(itemView.context)
                .load(String.format(ApiConfig.THUMBNAIL_URL, item.key))
                .into(imgThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listData[position])
    }

    fun setData(item: List<VideosItem>) {
        this.listData.clear()
        this.listData.addAll(item)
        notifyDataSetChanged()
    }
}