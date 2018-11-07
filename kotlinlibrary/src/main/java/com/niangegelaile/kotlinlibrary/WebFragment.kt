package com.niangegelaile.kotlinlibrary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class WebFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_anko, container, false)
        return UI {
            verticalLayout {
                val et = editText()
                button("OK") {
                    setOnClickListener {
                        toast("${et.text}")
                    }
                }
            }
        }.view
    }

    companion object {
        fun newInstance(): WebFragment {
            return WebFragment()
        }
    }

}