package com.niangegelaile.kotlinlibrary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class WebFragment : Fragment() {
   private var webView: WebView? = null;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_anko, container, false)
        return UI {
            verticalLayout {
                val et = editText()
                frameLayout {
                    webView=  webView {
                        settings.javaScriptEnabled = true
                        addJavascriptInterface(JsInterface(this), "android")
                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView, newProgress: Int) {

                            }
                        }
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                return true
                            }
                        }
                    }.lparams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

                    button("loadUrl") {
                        setOnClickListener {
                          loadUrl(et.text.toString())
                        }
                    }.lparams(ViewGroup.LayoutParams.WRAP_CONTENT,-2)
                }.lparams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

            }
        }.view
    }

    companion object {
        private val loadMethod="javascript:%1\$s(%2\$s)"
        fun newInstance(): WebFragment {
            return WebFragment()
        }
    }

    private fun loadUrl(url:String){
        webView!!.loadUrl(url)
    }
}