package com.example.bouncescrollbehaviour

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRecycler.setOnClickListener {
            val intent = Intent(this,RecyclerViewExample::class.java)
            startActivity(intent)

        }

        buttonScroll.setOnClickListener {
           /* val intent = Intent(this,RecyclerViewExample::class.java)
            startActivity(intent)*/
        }

    }
}
