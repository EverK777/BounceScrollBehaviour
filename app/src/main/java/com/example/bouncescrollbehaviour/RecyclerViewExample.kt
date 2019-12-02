package com.example.bouncescrollbehaviour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_recycler_view_example.*


class RecyclerViewExample : AppCompatActivity() {

    private val images: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_example)
        setData()

        val adapter = DynamicAdapter(
            layout = R.layout.item_recycler,
            entries = images,
            action = fun(_, view, item, position) {
            })
        verticalRecycler.setHasFixedSize(true)
        verticalRecycler.configureRecyclerGrid(4)
        verticalRecycler.adapter = adapter

        horizontalRecycler.setHasFixedSize(true)
        horizontalRecycler.configureRecycler()
        horizontalRecycler.adapter = adapter

    }

    private fun setData() {
        for (i in 1 until 20) {
            var imageName: String = "$i"
            if (i  < 10) {
                imageName = "00$i"
            } else if (i  < 100) {
                imageName = "0$i"
            }
            images.add("https://assets.pokemon.com/assets/cms2/img/pokedex/full/$imageName.png")
        }
    }

}

private fun androidx.recyclerview.widget.RecyclerView.configureRecyclerGrid(elementPerRow: Int = 3): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, elementPerRow)
    return this
}

private fun androidx.recyclerview.widget.RecyclerView.configureRecycler(): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
        context,
        androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
        false
    )
    return this
}




