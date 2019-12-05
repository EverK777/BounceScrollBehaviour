package com.example.bouncescrollbehaviour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_scroll_view_example.*

class ScrollViewExample : AppCompatActivity() {

    private val images: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view_example)
        //bounceScroll.activateBounceAnim = false


        setData()

        val adapter = DynamicAdapter(
            layout = R.layout.item_recycler,
            entries = images,
            action = fun(_, view, item, position) {
            })


        scrollRV.setHasFixedSize(true)
        scrollRV.configureRecycler()
        scrollRV.adapter = adapter




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
