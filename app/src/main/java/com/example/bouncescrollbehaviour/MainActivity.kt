package com.example.bouncescrollbehaviour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val letters :  ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for(i in 0 until 40){
            letters.add((i+10).toString())
        }

      val adapter =  DynamicAdapter(
            layout =R.layout.item_recycler,
            entries = letters,
            action = fun(_,view,item,position){

            })
       // bouncingRecycler.setHasFixedSize(true)
    //    bouncingRecycler.configureRecyclerGrid(4)
      //  bouncingRecycler.adapter =  adapter

    }


}
