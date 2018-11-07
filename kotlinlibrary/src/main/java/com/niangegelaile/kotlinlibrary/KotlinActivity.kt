package com.niangegelaile.kotlinlibrary


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Toast
import com.niangegelaile.kotlinlibrary.R
import org.jetbrains.anko.*


class KotlinActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=relativeLayout {
            frameLayout {
                id=R.id.action_bar_activity_content
                supportFragmentManager.beginTransaction().replace(id, WebFragment.newInstance()).commit()
            }.lparams {
                width=ViewGroup.LayoutParams.MATCH_PARENT
                height=ViewGroup.LayoutParams.MATCH_PARENT
            }
        }

    }
}