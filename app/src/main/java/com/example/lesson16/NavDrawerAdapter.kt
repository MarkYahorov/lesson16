package com.example.lesson16

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView

class NavDrawerAdapter(
    private val list: List<Category>,
    private val OnClick: (Category) -> Unit
) : RecyclerView.Adapter<NavDrawerAdapter.ViewHolder>() {

    class ViewHolder(item: View, private val OnClick: (Category) -> Unit) :
        RecyclerView.ViewHolder(item) {
        private val icon = item.findViewById<ImageView>(R.id.this_icon)
        private val text = item.findViewById<TextView>(R.id.this_text)
        private val layout = item.findViewById<ConstraintLayout>(R.id.item_container)

        fun bind(category: Category) {
            icon.setImageResource(category.icon)
            text.text = category.nameOfCategory
            layout.setOnClickListener {
                OnClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view, OnClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}