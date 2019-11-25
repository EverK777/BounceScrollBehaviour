package com.example.bouncescrollbehaviour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DynamicAdapter<T>(
    val layout: Int,
    val entries: List<T>,
    val action: (vh: ViewHolder, view: View, entry: T, position: Int) -> Unit
): androidx.recyclerview.widget.RecyclerView.Adapter<DynamicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        action.invoke(holder, holder.view, entries[position], position)
    }

    override fun getItemCount(): Int {
        return entries.size
    }
    class ViewHolder (view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val view = view
    }
}
