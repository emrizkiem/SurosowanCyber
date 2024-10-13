package com.example.surosowancyber.implement.features.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surosowancyber.R
import com.example.surosowancyber.implement.data.model.GenresItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

class GenreAdapter() : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private val listData: ArrayList<GenresItem> = arrayListOf()
    var onItemClick: ((GenresItem) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chipGroup: ChipGroup = itemView.findViewById(R.id.chip_genre)

        fun bindItems(items: List<GenresItem>) {
            chipGroup.removeAllViews()
            items.forEach { genre ->
                val chip = Chip(chipGroup.context).apply {
                    id = View.generateViewId()
                    text = genre.name
                    isCheckable = true
                    isCheckedIconVisible = false
                    chipStrokeColor = ContextCompat.getColorStateList(context, R.color.gray)
                    chipStrokeWidth = 2f
                    chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.bg_chip)
                    setTextColor(ContextCompat.getColorStateList(context, R.color.text_chip))
                    shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 12f)
                        .build()
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            onItemClick?.invoke(genre)
                        }
                    }
                }
                chipGroup.addView(chip)
            }

            if (chipGroup.childCount > 0) {
                (chipGroup.getChildAt(0) as? Chip)?.isChecked = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listData)
    }

    fun setData(item: List<GenresItem>) {
        this.listData.clear()
        this.listData.addAll(item)
        notifyDataSetChanged()
    }
}