package com.example.bouncescrollbehaviour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecyclerViewExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_example)
    }








    private fun androidx.recyclerview.widget.RecyclerView.configureRecyclerGrid(elementPerRow: Int = 3): androidx.recyclerview.widget.RecyclerView {
        itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        layoutManager = androidx.recyclerview.widget.GridLayoutManager(context,elementPerRow)
        return this
    }

    private fun androidx.recyclerview.widget.RecyclerView.configureRecycler(): androidx.recyclerview.widget.RecyclerView {
        itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false)
        return this
    }
}
