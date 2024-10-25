package com.example.surosowancyber.implement.features.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.MoviesItem
import com.example.surosowancyber.implement.data.network.ApiConfig

class RecommendedAdapter() : RecyclerView.Adapter<RecommendedAdapter.ViewHolder>() {
    private val listData: ArrayList<MoviesItem> = arrayListOf()
    var onItemClick: ((MoviesItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recommended, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: RecommendedAdapter.ViewHolder, position: Int) {
        holder.bindItems(listData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgRecommendMovies: ImageView = itemView.findViewById(R.id.img_recommend_movies)

        fun bindItems(items: MoviesItem) {
            imgRecommendMovies.setOnClickListener {
                onItemClick?.invoke(items)
            }

            Glide.with(itemView.context)
                .load(ApiConfig.IMAGE_BASE_URL + items.posterPath)
                .into(imgRecommendMovies)
        }
    }

    fun setData(item: List<MoviesItem>) {
        this.listData.clear()
        this.listData.addAll(item)
        notifyDataSetChanged()
    }
}