package com.niangegelaile.kotlinlibrary


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import org.jetbrains.anko.*


class KotlinActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.kactivity_kotlin)
//        val tv=findViewById<TextView>(R.id.kotlin_tv)
//        tv.setText("安哥你好")
        relativeLayout {
            button("lallalalalala") {
                setOnClickListener {
                    Toast.makeText(this@KotlinActivity, "点击了", Toast.LENGTH_LONG).show();
                }
            }.lparams {
                centerInParent()
            }
        }
    }
}