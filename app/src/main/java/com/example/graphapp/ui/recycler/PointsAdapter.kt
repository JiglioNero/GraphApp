package com.example.graphapp.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graphapp.R
import com.example.graphapp.data.entity.Point

class PointsAdapter(private val points: List<Point>) : RecyclerView.Adapter<BindableViewHolder<Point>>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<Point> {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.header_points, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_point, parent, false)
                PointViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder<Point>, position: Int) {
        if (position > 0) {
            holder.bind(points[position-1])
        }
    }

    override fun getItemViewType(position: Int) = if (position == 0) TYPE_HEADER else TYPE_ITEM

    override fun getItemCount(): Int = points.size + 1

    inner class PointViewHolder(itemView: View) : BindableViewHolder<Point>(itemView) {
        private val tvX: TextView = itemView.findViewById(R.id.tvHeaderX)
        private val tvY: TextView = itemView.findViewById(R.id.tvHeaderY)

        override fun bind(entity: Point) {
            tvX.text = entity.x.toString()
            tvY.text = entity.y.toString()
        }
    }

    inner class HeaderViewHolder(itemView: View) : BindableViewHolder<Point>(itemView) {
        override fun bind(entity: Point) {
            // do nothing
        }
    }
}