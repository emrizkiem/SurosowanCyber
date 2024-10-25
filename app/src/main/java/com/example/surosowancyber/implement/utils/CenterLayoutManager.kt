package com.example.surosowancyber.implement.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class CenterLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
    private val mShrinkAmount = 0.15f
    private val mShrinkDistance = 0.9f

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleChildren()
            return scrolled
        }
        return 0
    }

    private fun scaleChildren() {
        val midpoint = width / 2f
        val d0 = 0f
        val d1 = mShrinkDistance * midpoint
        val s0 = 1f
        val s1 = 1f - mShrinkAmount

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child?.let {
                val childMidpoint = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2f
                val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale
            }
        }
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()
    }
}