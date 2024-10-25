package com.example.surosowancyber.implement.features.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.MoviesItem
import com.example.surosowancyber.implement.data.network.ApiConfig

class NowPlayingAdapter() : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {
    private val listData: ArrayList<MoviesItem> = arrayListOf()
    var onItemClick: ((MoviesItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: ConstraintLayout = itemView.findViewById(R.id.now_playing)
        private val imgNowPlaying: ImageView = itemView.findViewById(R.id.img_now_playing)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.rating)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)

        fun bindItems(items: MoviesItem) {
            tvTitle.text = items.title
            tvDate.text = items.releaseDate
            ratingBar.rating = items.voteAverage.div(2)
            ratingBar.numStars = 5
            Glide.with(itemView.context)
                .load(ApiConfig.IMAGE_BASE_URL + items.posterPath)
                .into(imgNowPlaying)

            container.setOnClickListener {
                onItemClick?.invoke(items)
            }
        }
    }

    fun setData(item: List<MoviesItem>) {
        this.listData.clear()
        this.listData.addAll(item)
        notifyDataSetChanged()
    }
}